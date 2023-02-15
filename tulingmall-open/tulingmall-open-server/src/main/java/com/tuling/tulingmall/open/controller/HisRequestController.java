package com.tuling.tulingmall.open.controller;

import com.tuling.tulingmall.open.config.GsLogConfig;
import com.tuling.tulingmall.open.service.HisRequestService;
import com.tuling.tulingmall.open.util.GsConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author loulan
 * @desc
 */
@RestController
@RequestMapping("/hisrequest")
@ApiIgnore
public class HisRequestController {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private HisRequestService hisRequestService;

    @Resource
    private GsLogConfig gsLogConfig;

    @RequestMapping(value = "/getHistory", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getHistory(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> paras = new HashMap<>();
        paras.put("transId", request.getParameter("transId"));
        paras.put("serviceCode", request.getParameter("serviceCode"));
        res.put("code", "1");
        res.put("desc", "获取数据成功！");
        res.put("data", hisRequestService.getAll(paras));
        return res;
    }

    /**
     * 获取指定目录下transId文件列表
     */
    @RequestMapping(value = "/getTransIdLogFiles", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getTransIdLogFiles(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            //transId命名的日志文件存放的根目录
            String transIdLogFilesRootPath = gsLogConfig.getTransLogDir();
            String transId = request.getParameter("transId");
            //存储返回结果
            List<Map<String, String>> dataList = new ArrayList<>();
            List<File> files = (List<File>) FileUtils.listFiles(new File(transIdLogFilesRootPath), new String[]{GsConstants.LOG_SUFFIX}, false);
            if (!files.isEmpty()) {
                //按文件最后修改时间倒序排序
                files.sort((file1, file2) -> (int) (file2.lastModified() - file1.lastModified()));
                //如果transId不为空，只展现该transId的文件。 否则，返回最近的100个日志文件
                Predicate<File> logFileFilter = (f)-> f.getName().contains(transId.trim());
                if(StringUtils.isNotEmpty(transId)) {
                    files = files.stream().filter(logFileFilter).collect(Collectors.toList());
                }else if(files.size()>GsConstants.LOG_FILE_MAX_SIZE) {
                    files = files.subList(0, GsConstants.LOG_FILE_MAX_SIZE);
                }
                for (File file : files) {
                    Map<String, String> data = new HashMap<>();
                    data.put("transIdLogFileName", file.getName());
                    data.put("transIdLogFilePath", file.getAbsolutePath());
                    dataList.add(data);
                }
            }
            res.put("code", "1");
            res.put("desc", "获取数据成功！");
            res.put("data", dataList);
            res.put("logPath", transIdLogFilesRootPath);
        } catch (Exception ex) {
            res.put("code", "0");
            res.put("desc", "获取数据失败！");
            logger.error(ex.getMessage(), ex);
        }
        return res;
    }

    /**
     * 获取指定transId文件的内容
     */
    @RequestMapping(value = "/getTransIdLogFileContent", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getTransIdLogFileContent(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            //transId命名的日志文件存放的根目录
            String transIdLogFilePath = request.getParameter("transIdLogFilePath");
            String transIdLogFileName = request.getParameter("transIdLogFileName");
            String content = FileUtils.readFileToString(new File(transIdLogFilePath), "utf-8");
            Map<String, String> data = new HashMap<>();
            data.put("transIdLogFilePath", transIdLogFilePath);
            data.put("transIdLogFileName", transIdLogFileName);
            data.put("transIdLogFileContent", content);
            res.put("code", "1");
            res.put("desc", "获取数据成功！");
            res.put("data", data);
        } catch (Exception ex) {
            res.put("code", "0");
            res.put("desc", "获取数据失败！");
            logger.error(ex.getMessage(), ex);
        }
        return res;
    }
}
