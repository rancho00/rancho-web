package com.rancho.web.db.generator;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;

import java.util.List;

/**
 * 自定义java.mapper和mxl.mapper
 */
public class JavaClientAndXmlMapperGenerator extends IntrospectedTableMyBatis3Impl {


    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        return super.createJavaClientGenerator();
    }

    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
        xmlMapperGenerator = new InsoXMLMapperGenerator();

        initializeAbstractGenerator(xmlMapperGenerator, warnings,progressCallback);
    }

    class InsoXMLMapperGenerator extends XMLMapperGenerator {
        @Override
        protected XmlElement getSqlMapElement() {
            return super.getSqlMapElement();
//            FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
//            this.progressCallback.startTask(Messages.getString("Progress.12", table.toString()));
//            XmlElement answer = new XmlElement("mapper");
//            String namespace = this.introspectedTable.getMyBatis3SqlMapNamespace();
//            answer.addAttribute(new Attribute("namespace", namespace));
//            this.context.getCommentGenerator().addRootComment(answer);
//            this.addInsertElement(answer);
//            return answer;
        }

        protected void addInsertElement(XmlElement parentElement) {
            if (this.introspectedTable.getRules().generateInsert()) {
                AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator(false);
                this.initializeAndExecuteGenerator(elementGenerator, parentElement);
            }

        }
    }
}
