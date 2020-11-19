/**
 * Copyright 2019 蒋文忠
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.common.license;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author jiang wenzhong
 * @date 2019-07-23
 */
public class AddLicenseWithFileFileAction implements FileAction {

    /**
     * 2M
     */
    public static final int MAX_FILE_SIZE = 2 * 1024 * 1024;
    private String mLicensePath;

    public AddLicenseWithFileFileAction(String licensePath) {

        this.mLicensePath = licensePath;
    }

    @Override
    public void handleFile(File file) {

        RandomAccessFile targetRandomAccessFile = null;
        RandomAccessFile licenseRandomAccessFile = null;
        try {
            targetRandomAccessFile = new RandomAccessFile(file, "rw");
            licenseRandomAccessFile = new RandomAccessFile(mLicensePath, "rw");

            if (targetRandomAccessFile.length() > MAX_FILE_SIZE) {
                System.out.println("file size is too long!" + file.getName());
                return;
            }

            // 读取文本内容
            byte[] contentBytes = new byte[(int) targetRandomAccessFile.length()];
            targetRandomAccessFile.readFully(contentBytes);
            String contentStr = new String(contentBytes);

            // 读取license文本内容
            byte[] licenseBytes = new byte[(int) licenseRandomAccessFile.length()];
            licenseRandomAccessFile.readFully(licenseBytes);
            String licenseStr = new String(licenseBytes);

            int indexOfPackage = contentStr.indexOf("package");
            // 拼接最终的文件内容
            contentStr = licenseStr + "\n" + contentStr.substring(indexOfPackage);
            targetRandomAccessFile.seek(0);
            targetRandomAccessFile.setLength(contentStr.length());
            targetRandomAccessFile.write(contentStr.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (targetRandomAccessFile != null) {
                    targetRandomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (licenseRandomAccessFile != null) {
                try {
                    licenseRandomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
