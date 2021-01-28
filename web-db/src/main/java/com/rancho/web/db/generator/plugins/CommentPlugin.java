package com.rancho.web.db.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 *  @Author: GuiRunning 郭贵荣
 *
 *  @Description: 自定义注释生成
 *
 *  @Date: 2018/7/14 0:57
 *
 */
public class CommentPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.getJavaDocLines().clear();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * Table: " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" */");
        return true;
    }

    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        this.comment(field, introspectedTable, introspectedColumn);
        return true;
    }

    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return true;
    }

    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return true;
    }

    private void comment(JavaElement element, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        element.getJavaDocLines().clear();
        element.addJavaDocLine("/**");
        String remark = introspectedColumn.getRemarks();
        if (remark != null && remark.length() > 1) {
            element.addJavaDocLine(" * " + remark);
            element.addJavaDocLine(" *");
        }

        element.addJavaDocLine(" * Table:     " + introspectedTable.getFullyQualifiedTable());
        element.addJavaDocLine(" * Column:    " + introspectedColumn.getActualColumnName());
        element.addJavaDocLine(" * Nullable:  " + introspectedColumn.isNullable());
        element.addJavaDocLine(" */");
    }

    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.commentResultMap(element, introspectedTable);
        return true;
    }

    public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.commentResultMap(element, introspectedTable);
        return true;
    }

    void commentResultMap(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> es = element.getElements();
        if (!es.isEmpty()) {
            String alias = introspectedTable.getTableConfiguration().getAlias();
            int aliasLen = -1;
            if (alias != null) {
                aliasLen = alias.length() + 1;
            }

            Iterator<Element> it = es.iterator();
            HashMap map = new HashMap();

            while(true) {
                while(it.hasNext()) {
                    Element e = (Element)it.next();
                    if (e instanceof TextElement) {
                        it.remove();
                    } else {
                        XmlElement el = (XmlElement)e;
                        List<Attribute> as = el.getAttributes();
                        if (!as.isEmpty()) {
                            String col = null;
                            Iterator i$ = as.iterator();

                            while(i$.hasNext()) {
                                Attribute a = (Attribute)i$.next();
                                if (a.getName().equalsIgnoreCase("column")) {
                                    col = a.getValue();
                                    break;
                                }
                            }

                            if (col != null) {
                                if (aliasLen > 0) {
                                    col = col.substring(aliasLen);
                                }

                                IntrospectedColumn ic = introspectedTable.getColumn(col);
                                if (ic != null) {
                                    StringBuilder sb = new StringBuilder();
                                    if (ic.getRemarks() != null && ic.getRemarks().length() > 1) {
                                        sb.append("<!-- ");
                                        sb.append(ic.getRemarks());
                                        sb.append(" -->");
                                        map.put(el, new TextElement(sb.toString()));
                                    }
                                }
                            }
                        }
                    }
                }

                if (map.isEmpty()) {
                    return;
                }

                Set<Element> set = map.keySet();
                Iterator i$ = set.iterator();

                while(i$.hasNext()) {
                    Element e = (Element)i$.next();
                    int id = es.indexOf(e);
                    es.add(id, (Element) map.get(e));
                    es.add(id, new TextElement(""));
                }

                return;
            }
        }
    }

    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.removeAttribute(element.getAttributes(), "parameterType");
        return true;
    }

    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.removeAttribute(element.getAttributes(), "parameterType");
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.removeAttribute(element.getAttributes(), "parameterType");
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.removeAttribute(element.getAttributes(), "parameterType");
        return true;
    }

    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.removeAttribute(element.getAttributes(), "parameterType");
        return true;
    }

    private void removeAttribute(List<Attribute> as, String name) {
        if (!as.isEmpty()) {
            Iterator it = as.iterator();

            Attribute attr;
            do {
                if (!it.hasNext()) {
                    return;
                }

                attr = (Attribute)it.next();
            } while(!attr.getName().equalsIgnoreCase(name));

            it.remove();
        }
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        document.getRootElement().addElement(new TextElement(""));
        document.getRootElement().addElement(new TextElement("<!-- ### 以上代码由MBG + CommentPlugin自动生成, 生成时间: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + " ### -->\n\n\n"));
        document.getRootElement().addElement(new TextElement("<!-- Your codes goes here!!! -->"));
        document.getRootElement().addElement(new TextElement(""));
        return true;
    }
}
