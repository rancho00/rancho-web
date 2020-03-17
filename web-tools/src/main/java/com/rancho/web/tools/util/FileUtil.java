package com.rancho.web.tools.util;

import com.rancho.web.tools.config.SysCommonConfig;
import com.rancho.web.common.result.CommonResult;
import com.rancho.web.tools.domain.FileVo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Resource
    private SysCommonConfig sysCommonConfig;

    @Resource
    private HttpServletRequest request;

    /**
     * 上传
     * @param file
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public CommonResult<FileVo> upload(MultipartFile file, String folderName){
        if(file==null){
            return null;
        }
        try{
            String first;
            String second="";
            if("on".equals(sysCommonConfig.getUploadProject())){
                second="/resource";
                first = WebUtils.getRealPath(request.getSession().getServletContext(), second);
            }else{
                String os=System.getProperty("os.name");
                if(os.contains("Win")){
                    //window
                    first=sysCommonConfig.getWindowsPrefix();
                }else{
                    //linux
                    first=sysCommonConfig.getLinuxPrefix();
                }
            }


            FileVo fr=new FileVo();
            //判断文件类型
            String fileName=file.getOriginalFilename();
            String third;
            if(sysCommonConfig.getImageIncludes().contains(fileName.split("\\.")[1])){
                third= sysCommonConfig.getImageUploadPrefix();
                //fr.setType(1);
            }else if(sysCommonConfig.getVideoIncludes().contains(fileName.split("\\.")[1])){
                third=sysCommonConfig.getVideoUploadPrefix();
                //fr.setType(2);
            }else if(sysCommonConfig.getFileIncludes().contains(fileName.split("\\.")[1])){
                third=sysCommonConfig.getFileUploadPrefix();
                //fr.setType(3);
            }else{
                return CommonResult.failed("无效文件类型或者不支持该文件类型");
            }
            //文件所属文件夹
            if(folderName==null || "".equals(folderName)){
                return CommonResult.failed("folderName为空");
            }else if(!sysCommonConfig.getFolderName().contains(folderName)){
                return CommonResult.failed("不支持"+folderName+"文件夹类型");
            }
            String fourth="/"+folderName;
            //文件夹不存在就创建
            String folderPath=first+third+fourth;
            File folder=new File(folderPath);
            if(!folder.exists()){
                folder.mkdirs();
            }
            String name="/"+ UUID.randomUUID()+"." + fileName.split("\\.")[1];
            String fullPath=folderPath+name;
            //上传
            String basePath=second+third+fourth+name;
            file.transferTo(new File(fullPath));
            fr.setBaseUrl(basePath);
            fr.setVisitUrl(request.getScheme()+ "://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+basePath);
            return CommonResult.success(fr);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed();
        }
    }


    /**
     * 删除
     * @param url
     * @return
     */
//    public static CommonResult delete(String url){
//        try {
//            File file=new File(url);
//            file.delete();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return CommonResult.success();
//    }
}
