package com.rogrand.kkmy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.common.page.Pagination;
import com.rogrand.kkmy.merchant.api.KkmyMerchantService;
import com.rogrand.kkmy.merchant.model.KkmyMerchant;
import com.rogrand.kkmy.parent.util.GeographyUtil;
import com.rogrand.kkmy.util.FtpUpload;
import com.rogrand.kkmy.util.JsonUtils;

/**
 * 版权：融贯资讯 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-4-23 <br/>
 * 描述：〈商户数据修正〉
 */
public class MerchantDataRevise extends BaseTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    private KkmyMerchantService kkmyMerchantService;

    private List<String> merchantPictures;
    private List<String> merchantFiles;

    private FtpUpload uploader = new FtpUpload();

    @Test
    public void revise2() throws Exception {
        boolean connected = uploader.connect("img.kkmaiyao.com", 21, "kkmaiyao", "kkmaiyao@2013", "/");
        if (connected) {
            int totalCount = kkmyMerchantService.getMerchantCount();

            Pagination<KkmyMerchant> pagination = new Pagination<KkmyMerchant>(1, 100, totalCount);

            List<KkmyMerchant> list = null;

            String merchantTel = null;
            String merchantRegTime = null;
            String merchantPic = null;
            String merchantQualification1 = null;
            String merchantQualification2 = null;
            String merchantQualification3 = null;
            for (int i = 1, n = pagination.getTotalPage(); i <= n; i++) {
                pagination = new Pagination<KkmyMerchant>(i, 100, totalCount);

                list = kkmyMerchantService.getMerchantList(pagination).getList();

                for (KkmyMerchant bean : list) {
                    merchantTel = bean.getMerchantTel();
                    
                    if (StringUtils.isEmpty(merchantTel)) {
                        continue;
                    }
                    
                    System.out.println("====================================================");
                    System.out.println("修改商户ID：" + bean.getMerchantId() + "\t账号：" + merchantTel);

                    merchantRegTime = format.format(bean.getMerchantregtime());

                    merchantPic = uploader.existFile("/kkmy/upload/picture/merchant/" + merchantRegTime + "/"
                            + merchantTel);
                    merchantQualification1 = uploader.existFile("/kkmy/upload/picture/merchant/" + merchantRegTime
                            + "/" + merchantTel + "_a1");
                    merchantQualification2 = uploader.existFile("/kkmy/upload/picture/merchant/" + merchantRegTime
                            + "/" + merchantTel + "_a2");
                    merchantQualification3 = uploader.existFile("/kkmy/upload/picture/merchant/" + merchantRegTime
                            + "/" + merchantTel + "_a3");

                    System.out.println("merchantPic = " + merchantPic);
                    System.out.println("merchantQualification1 = " + merchantQualification1);
                    System.out.println("merchantQualification2 = " + merchantQualification2);
                    System.out.println("merchantQualification3 = " + merchantQualification3);

                    KkmyMerchant merchant = new KkmyMerchant();
                    merchant.setMerchantId(bean.getMerchantId());
                    merchant.setMerchantPic(StringUtils.defaultString(merchantPic));
                    merchant.setMerchantQualification1(StringUtils.defaultString(merchantQualification1));
                    merchant.setMerchantQualification2(StringUtils.defaultString(merchantQualification2));
                    merchant.setMerchantQualification3(StringUtils.defaultString(merchantQualification3));

                    if (kkmyMerchantService.updateByPrimaryKeySelective(merchant) > 0) {
                        System.out.println("保存商户数据修改!");
                    } else {
                        System.out.println("保存修改失败！！！！");
                    }

                    System.out.println("====================================================\n");
                }
            }
        }
    }

    @Test
    public void revise() throws Exception {
        boolean connected = uploader.connect("img.kkmaiyao.com", 21, "kkmaiyao", "kkmaiyao@2013", "/");
        // boolean connected = uploader.connect("img2.kkmaiyao.com", 21,
        // "kkmytest", "kkmytest20131202", "/");
        if (connected) {
            {// 搜集正式环境FTP商户头像及资质
                merchantPictures = uploader.getFileList("/kkmy/upload/pic/");
                merchantFiles = uploader.getFileList("/kkmy/upload/", new FTPFileFilter() {
                    @Override
                    public boolean accept(FTPFile file) {
                        String extension = FilenameUtils.getExtension(file.getName());
                        return !(extension.equalsIgnoreCase("spx") || extension.equalsIgnoreCase("mp3"));
                    }
                });

                System.out.println("merchantPictures = " + JsonUtils.toJsonString(merchantPictures));
                System.out.println("merchantFiles = " + JsonUtils.toJsonString(merchantFiles));
            }

            {
                int totalCount = kkmyMerchantService.getMerchantCount();

                Pagination<KkmyMerchant> pagination = new Pagination<KkmyMerchant>(1, 100, totalCount);

                List<KkmyMerchant> list = null;
                for (int i = 1, n = pagination.getTotalPage(); i <= n; i++) {
                    pagination = new Pagination<KkmyMerchant>(i, 100, totalCount);

                    list = kkmyMerchantService.getMerchantList(pagination).getList();

                    for (KkmyMerchant bean : list) {
                        System.out.println("====================================================");
                        System.out.println("修改商户ID：" + bean.getMerchantId() + "\t账号：" + bean.getMerchantTel());
                        KkmyMerchant merchant = new KkmyMerchant();

                        merchant.setMerchantId(bean.getMerchantId());
                        merchant.setMerchantTel(bean.getMerchantTel());

                        // 注册时间为空的数据处理
                        if (bean.getMerchantregtime() == null) {
                            merchant.setMerchantregtime(new Date());
                        } else {
                            merchant.setMerchantregtime(bean.getMerchantregtime());
                        }

                        // 修正默认配送距离
                        if (bean.getSendDistance() != null && bean.getSendDistance().doubleValue() != 0) {
                            merchant.setSendDistance(bean.getSendDistance());
                        } else {
                            merchant.setSendDistance(5d);
                        }

                        {// 商户头像地址修正
                            String merchantPic = bean.getMerchantPic();
                            if (StringUtils.isNotEmpty(merchantPic)) {
                                if (merchantPictures.contains(merchantPic)) {
                                    movePicture(merchant, merchantPic);
                                } else {
                                    System.out.println("无商户头像!");
                                    if (!merchantPic.startsWith("/")) {
                                        merchant.setMerchantPic("");
                                    }
                                }
                            } else {
                                merchantPic = isExistPicture(merchant.getMerchantTel());
                                if (StringUtils.isNotEmpty(merchantPic)) {
                                    movePicture(merchant, merchantPic);
                                } else {
                                    System.out.println("无商户头像文件!");
                                }
                            }
                        }

                        {// 商户资质地址修正
                            moveFile(merchant, 1);
                            moveFile(merchant, 2);
                            moveFile(merchant, 3);
                        }

                        {// 商户接单范围修改
                            if ((bean.getMerchantLatitude() != null && bean.getMerchantLatitude().doubleValue() != 0)
                                    && (bean.getMerchantLongitude() != null && bean.getMerchantLongitude()
                                            .doubleValue() != 0)) {
                                double[] lat = GeographyUtil.getLat(bean.getMerchantLatitude(),
                                        bean.getMerchantLongitude(), merchant.getSendDistance());
                                double[] lng = GeographyUtil.getLng(bean.getMerchantLatitude(),
                                        bean.getMerchantLongitude(), merchant.getSendDistance());
                                merchant.setSendLatitudeFrom(lat[0]);
                                merchant.setSendLatitudeTo(lat[1]);
                                merchant.setSendLongitudeFrom(lng[0]);
                                merchant.setSendLongitudeTo(lng[1]);
                                System.out.println("修改商户经纬度!");
                            }
                        }

                        if (kkmyMerchantService.updateByPrimaryKeySelective(merchant) > 0) {
                            System.out.println("保存商户数据修改!");
                        } else {
                            System.out.println("保存修改失败！！！！");
                        }
                        System.out.println("====================================================\n");
                    }
                }
            }
        }
        uploader.disconnect();
    }

    /**
     * 描述：〈移动商户头像〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-4-23 <br/>
     * 
     * @param merchant 商户信息
     * @param merchantPic 商户头像全称
     * @return
     * @throws IOException
     */
    private boolean movePicture(KkmyMerchant merchant, String merchantPic) throws IOException {
        String oldPath = "/kkmy/upload/pic/" + merchantPic;
        String newPath = "/kkmy/upload/picture/merchant/" + format.format(merchant.getMerchantregtime()) + "/"
                + merchantPic.toLowerCase();
        if (uploader.moveFile(oldPath, newPath)) {
            System.out.println("移动头像 " + oldPath + " 到 " + newPath + " 成功！");
            merchant.setMerchantPic(newPath);

            merchantPictures.remove(merchantPic);
            return true;
        } else {
            System.out.println("移动头像失败： " + oldPath + " 到 " + newPath);
        }

        return false;
    }

    /**
     * 描述：〈移动商户头像〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-4-23 <br/>
     * 
     * @param merchant 商户信息
     * @param merchantPic 商户头像全称
     * @return
     * @throws IOException
     */
    private boolean moveFile(KkmyMerchant merchant, String merchantFile, int index) throws IOException {
        String oldPath = "/kkmy/upload/" + merchantFile;
        String newPath = "/kkmy/upload/picture/merchant/" + format.format(merchant.getMerchantregtime()) + "/"
                + merchantFile.toLowerCase();
        if (uploader.moveFile(oldPath, newPath)) {
            System.out.println("移动资质 " + oldPath + " 到 " + newPath + " 成功！");
            switch (index) {
            case 1:
                merchant.setMerchantQualification1(newPath);
                break;
            case 2:
                merchant.setMerchantQualification2(newPath);
                break;
            case 3:
                merchant.setMerchantQualification3(newPath);
                break;
            default:
                break;
            }

            merchantFiles.remove(merchantFile);
            return true;
        } else {
            System.out.println("移动资质失败： " + oldPath + " 到 " + newPath);
        }

        return false;
    }

    /**
     * 描述：〈移动商户资质〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-4-23 <br/>
     * 
     * @param merchant 商户信息
     * @param index 商户资质索引
     * @return
     * @throws IOException
     */
    private void moveFile(KkmyMerchant merchant, int index) throws IOException {
        String merchantFile = null;

        switch (index) {
        case 1:
            merchantFile = merchant.getMerchantQualification1();
            merchant.setMerchantQualification1("");
            break;
        case 2:
            merchantFile = merchant.getMerchantQualification2();
            merchant.setMerchantQualification2("");
            break;
        case 3:
            merchantFile = merchant.getMerchantQualification3();
            merchant.setMerchantQualification3("");
            break;
        default:
            break;
        }

        if (StringUtils.isNotEmpty(merchantFile)) {
            if (merchantFiles.contains(merchantFile)) {
                moveFile(merchant, merchantFile, index);
            }
        } else {
            merchantFile = isExistFile(merchant.getMerchantTel() + "_a" + index);
            if (StringUtils.isNotEmpty(merchantFile)) {
                moveFile(merchant, merchantFile, index);
            }
        }
    }

    /**
     * 描述：〈判断FTP中是否存在商户图片，如果存在则返回图片全称〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-4-23 <br/>
     * 
     * @param merchantPictureName 商户头像名称
     * @return
     */
    private String isExistPicture(String merchantPictureName) {
        for (String name : merchantPictures) {
            if (FilenameUtils.getBaseName(name).equals(merchantPictureName)) {
                return name;
            }
        }

        return null;
    }

    private String isExistFile(String merchantFileName) {
        for (String name : merchantFiles) {
            if (FilenameUtils.getBaseName(name).equals(merchantFileName)) {
                return name;
            }
        }

        return null;
    }
}
