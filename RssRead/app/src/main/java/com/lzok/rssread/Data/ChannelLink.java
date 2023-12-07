package com.lzok.rssread.Data;

public class ChannelLink {
        private String link;
        private String channelName;

        public ChannelLink(String link, String channelName) {
            this.link = link;
            this.channelName = channelName;
        }

        public String getLink() {
            return link;
        }

        public String getChannelName() {
            return channelName;
        }


}
