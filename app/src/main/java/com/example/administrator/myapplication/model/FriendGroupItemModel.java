package com.example.administrator.myapplication.model;

import com.example.administrator.myapplication.Config;

import java.util.List;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-26
 */
public class FriendGroupItemModel {

    /**
     * forwardRemindText : 某某转发了
     * userNameText : CC
     * userIdText : 1
     * userMarkText : 无线事业部
     * userHeaderImageName : http://e.hiphotos.baidu.com/image/h%3D200/sign=a0901680a3c27d1eba263cc42bd4adaf/b21bb051f819861842d54ba04ded2e738bd4e600.jpg
     * userTimeText : 刚刚
     * contentText : 新华社北京3月16日电 第十二届全国人民代表大会第四次会议圆满完成各项议程16日上午在人民大会堂闭幕。大会批准政府工作报告、“十三五”规划纲要、全国人大常委会工作报告等；通过慈善法，国家主席习近平签署第43号主席令予以公布
     * imageArray : ["http://ww2.sinaimg.cn/thumbnail/9ecab84ejw1emgd5nd6eaj20c80c8q4a.jpg","http://ww2.sinaimg.cn/thumbnail/642beb18gw1ep3629gfm0g206o050b2a.gif","http://ww4.sinaimg.cn/thumbnail/9e9cb0c9jw1ep7nlyu8waj20c80kptae.jpg","http://ww3.sinaimg.cn/thumbnail/8e88b0c1gw1e9lpr1xydcj20gy0o9q6s.jpg","http://ww2.sinaimg.cn/thumbnail/8e88b0c1gw1e9lpr2n1jjj20gy0o9tcc.jpg","http://ww4.sinaimg.cn/thumbnail/8e88b0c1gw1e9lpr4nndfj20gy0o9q6i.jpg","http://ww3.sinaimg.cn/thumbnail/8e88b0c1gw1e9lpr57tn9j20gy0obn0f.jpg"]
     * praise : 1,5
     * comment : 1,10
     * commentArray : [{"criticidname":"某某转发了","targetidname":"锐萌","content":"提取最足球足球"},{"criticidname":"瑞文","content":"提取最足球足球sss"}]
     */

    private int type = Config.NORMAL;
    private EzContentDataBean ezContentData;

    public EzContentDataBean getEzContentData() {
        return ezContentData;
    }

    public void setEzContentData(EzContentDataBean ezContentData) {
        this.ezContentData = ezContentData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class EzContentDataBean {
        private String forwardRemindText;
        private String userNameText;
        private String userIdText;
        private String userMarkText;
        private String userHeaderImageName;
        private String userTimeText;
        private String contentText;
        private String praise;
        private String comment;
        private List<String> imageArray;
        /**
         * criticidname : 某某转发了
         * targetidname : 锐萌
         * content : 提取最足球足球
         */

        private List<CommentArrayBean> commentArray;

        public String getForwardRemindText() {
            return forwardRemindText;
        }

        public void setForwardRemindText(String forwardRemindText) {
            this.forwardRemindText = forwardRemindText;
        }

        public String getUserNameText() {
            return userNameText;
        }

        public void setUserNameText(String userNameText) {
            this.userNameText = userNameText;
        }

        public String getUserIdText() {
            return userIdText;
        }

        public void setUserIdText(String userIdText) {
            this.userIdText = userIdText;
        }

        public String getUserMarkText() {
            return userMarkText;
        }

        public void setUserMarkText(String userMarkText) {
            this.userMarkText = userMarkText;
        }

        public String getUserHeaderImageName() {
            return userHeaderImageName;
        }

        public void setUserHeaderImageName(String userHeaderImageName) {
            this.userHeaderImageName = userHeaderImageName;
        }

        public String getUserTimeText() {
            return userTimeText;
        }

        public void setUserTimeText(String userTimeText) {
            this.userTimeText = userTimeText;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<String> getImageArray() {
            return imageArray;
        }

        public void setImageArray(List<String> imageArray) {
            this.imageArray = imageArray;
        }

        public List<CommentArrayBean> getCommentArray() {
            return commentArray;
        }

        public void setCommentArray(List<CommentArrayBean> commentArray) {
            this.commentArray = commentArray;
        }

        public static class CommentArrayBean {
            private String criticidname;
            private String targetidname;
            private String content;

            public String getCriticidname() {
                return criticidname;
            }

            public void setCriticidname(String criticidname) {
                this.criticidname = criticidname;
            }

            public String getTargetidname() {
                return targetidname;
            }

            public void setTargetidname(String targetidname) {
                this.targetidname = targetidname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
