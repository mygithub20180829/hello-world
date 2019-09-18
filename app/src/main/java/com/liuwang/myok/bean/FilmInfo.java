package com.liuwang.myok.bean;

import java.util.List;

public class FilmInfo {

    /**
     * code : 0
     * list : {"0":{"aid":"6008965","author":"哔哩哔哩番剧","coins":170,"copyright":"Copy","create":"2016-08-25 21:34"},"1":{"aid":"6008938","author":"哔哩哔哩番剧","coins":404,"copyright":"Copy","create":"2016-08-25 21:33"}}
     */

    private int code;
    private List<FilmBean> list;

    @Override
    public String toString() {
        return "FilmInfo{" +
                "code=" + code +
                ", list=" + list +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FilmBean> getList() {
        return list;
    }

    public void setList(List<FilmBean> list) {
        this.list = list;
    }

    public static class FilmBean {
        /**
         * 0 : {"aid":"6008965","author":"哔哩哔哩番剧","coins":170,"copyright":"Copy","create":"2016-08-25 21:34"}
         * 1 : {"aid":"6008938","author":"哔哩哔哩番剧","coins":404,"copyright":"Copy","create":"2016-08-25 21:33"}
         */

            private String aid;
            private String author;
            private int coins;
            private String copyright;
            private String create;

        @Override
        public String toString() {
            return "ListBean{" +
                    "aid='" + aid + '\'' +
                    ", author='" + author + '\'' +
                    ", coins=" + coins +
                    ", copyright='" + copyright + '\'' +
                    ", create='" + create + '\'' +
                    '}';
        }

        public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public String getCreate() {
                return create;
            }

            public void setCreate(String create) {
                this.create = create;
            }
    }
}
