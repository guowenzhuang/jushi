package com.jushi.oss.handler;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.jushi.api.pojo.Result;
import com.jushi.oss.pojo.consts.OssConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * @author 80795
 * @date 2019/7/17 21:06
 */
@Component
public class OssHandler {


    @Autowired
    private OSS oss;

    /**
     * 文件上传
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> upload(ServerRequest request) {
        Mono<MultiValueMap<String, Part>> multiValueMapMono = request.body(BodyExtractors.toMultipartData());
        return multiValueMapMono.flatMap(multiValueMap -> {
            List<Part> editor = multiValueMap.get("editor");
            FilePart filePart = (FilePart) editor.get(0);
            FormFieldPart formFieldPart = (FormFieldPart) multiValueMap.get("path").get(0);
            String prefix = formFieldPart.value();
            try {
                Path filePath = Files.createTempFile("oss", filePart.filename());
                File file = filePath.toFile();
                filePart.transferTo(file);
                InputStream inputStream = new FileInputStream(file);

                String fileName = prefix + UUID.randomUUID() +filePart.filename();
                PutObjectResult putObjectResult = oss.putObject(OssConst.SPRING_OSS_BUCKET_NAME,
                        fileName, inputStream);

                return ServerResponse.ok().body(Mono.just(Result.success("上次成功", OssConst.PATH_PREFIX + fileName)), Result.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ServerResponse.ok().body(Mono.just(Result.error("文件上传失败")), Result.class);
        });
    }
}
