package com.rogrand.kkmy;

import java.io.File;
import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.service.UploadService;
import com.rogrand.kkmy.util.UploadType;

/**
 * 版权：融贯资讯 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-4-3 <br/>
 * 描述：〈描述〉
 */
public class UploadServiceTest extends BaseTest {

    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void moveFile() {
        try {
            String newPath = uploadService.moveTemp(UploadType.IMAGE,
                    "/kkmy/upload/temp/031ecb1081b3499fa0cb29028b40445b.jpg");
            System.out.println(newPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void mongoUpload() throws IOException {
        GridFS gridFS = new GridFS(mongoTemplate.getDb());

        File file = new File("D:\\Users\\Administrator\\Pictures\\gif\\1.gif");
        GridFSInputFile mongofile = gridFS.createFile(file);
        ObjectId objectId = (ObjectId) mongofile.getId();
        mongofile.save();
        System.out.println("ImageId:" + objectId.toString());
    }
}
