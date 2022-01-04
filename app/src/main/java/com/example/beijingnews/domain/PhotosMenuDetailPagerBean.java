package com.example.beijingnews.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 图组详情页面
 */

public class PhotosMenuDetailPagerBean {

    private int retcode;

    private DataDTO data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }


    public static class DataDTO implements Serializable {

        private String title;

        private String countcommenturl;

        private String more;

        private List<?> topic;

        private List<NewsDTO> news;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public List<?> getTopic() {
            return topic;
        }

        public void setTopic(List<?> topic) {
            this.topic = topic;
        }

        public List<NewsDTO> getNews() {
            return news;
        }

        public void setNews(List<NewsDTO> news) {
            this.news = news;
        }


        public static class NewsDTO implements Serializable {
            private int id;

            private String title;

            private String url;

            private String listimage;

            private String smallimage;

            private String largeimage;

            private String pubdate;

            private Boolean comment;

            private String commenturl;

            private String type;

            private String commentlist;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getListimage() {
                return listimage;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public String getSmallimage() {
                return smallimage;
            }

            public void setSmallimage(String smallimage) {
                this.smallimage = smallimage;
            }

            public String getLargeimage() {
                return largeimage;
            }

            public void setLargeimage(String largeimage) {
                this.largeimage = largeimage;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public Boolean isComment() {
                return comment;
            }

            public void setComment(Boolean comment) {
                this.comment = comment;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCommentlist() {
                return commentlist;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }
        }
    }
}
