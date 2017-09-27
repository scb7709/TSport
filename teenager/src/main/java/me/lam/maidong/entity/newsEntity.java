package me.lam.maidong.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 */
public class newsEntity {
    /**
     * Status : 1
     * NewsList : [.联系电话：010-65535698微信号：","ArticleTitle":"几个简单运动\u201c处方\u201d治疗身体小疾病","Author":"迈动健康","ArticleCreated":"2015/7/23 9:43:27","ID":"97","ArticleCategoryName":"健身指导","ImgUrl":"","Source":"三联阅读"},{"ArticleModified":"2015/7/17 9:58:38","ArticleCategoryID":"1","Content":"\n","ArticleTitle":"血管堵塞","Author":"迈动健康","ArticleCreated":"2015/7/17 9:58:38","ID":"95","ArticleCategoryName":"新闻动态","ImgUrl":"","Source":"迈动健康"}]
     */
    private int MyStatus;
    private List<NewsListEntity> NewsList;

    @Override
    public String toString() {
        return "newsEntity{" +
                "Status=" + MyStatus +
                ", NewsList=" + NewsList +
                '}';
    }

    public void setStatus(int Status) {
        this.MyStatus = Status;
    }

    public void setNewsList(List<NewsListEntity> NewsList) {
        this.NewsList = NewsList;
    }

    public int getStatus() {
        return MyStatus;
    }

    public List<NewsListEntity> getNewsList() {
        return NewsList;
    }

    public class NewsListEntity {
        @Override
        public String toString() {
            return "NewsListEntity{" +
                    "ArticleModified='" + ArticleModified + '\'' +
                    ", ArticleCategoryID='" + ArticleCategoryID + '\'' +
                    ", Content='" + Content + '\'' +
                    ", ArticleTitle='" + ArticleTitle + '\'' +
                    ", Author='" + Author + '\'' +
                    ", ArticleCreated='" + ArticleCreated + '\'' +
                    ", ID='" + ID + '\'' +
                    ", ArticleCategoryName='" + ArticleCategoryName + '\'' +
                    ", ImgUrl='" + ImgUrl + '\'' +
                    ", Source='" + Source + '\'' +
                    '}';
        }

        /**
         * ArticleModified : 2015/12/10 9:50:31
         * ArticleCategoryID : 8
         * Content : 北京市于12月8日7时至12月10日12时启动空气重污染红色预警。这也是北京首次启动空气重污染红色预警。每当这个时候，关于雾霾的各种解读都十分流行，其中不乏真知灼见，也混淆着许多谣言。例如果壳网近日发布的《朋友圈里的0026;航拍北京雾霾0026;、0026;鲜肺6天变黑0026;是真的吗？》，就是很及时的辟谣文章。科学的了解和正确的防护指导尤为重要。我国环保部与环境科学学会联合编制了《公众防护2.5科普宣传册》，@乐动慢活全文登载宣传册内容，请将知识扩散给身边的朋友哦！
         * ArticleTitle : 最科学的雾霾防护手册，环保部权威发布！请转给身边的亲友
         * Author : 迈动健康
         * ArticleCreated : 2015/12/10 9:50:31
         * ID : 112
         * ArticleCategoryName : 运动宝典
         * ImgUrl : http://182.92.215.55//UploadFiles//images//2015/12/10//76.jpg
         * Source : 乐动慢活
         */
        private String ArticleModified;
        private String ArticleCategoryID;
        private String Content;
        private String ArticleTitle;
        private String Author;
        private String ArticleCreated;
        private String ID;
        private String ArticleCategoryName;
        private String ImgUrl;
        private String Source;

        public void setArticleModified(String ArticleModified) {
            this.ArticleModified = ArticleModified;
        }

        public void setArticleCategoryID(String ArticleCategoryID) {
            this.ArticleCategoryID = ArticleCategoryID;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public void setArticleTitle(String ArticleTitle) {
            this.ArticleTitle = ArticleTitle;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public void setArticleCreated(String ArticleCreated) {
            this.ArticleCreated = ArticleCreated;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setArticleCategoryName(String ArticleCategoryName) {
            this.ArticleCategoryName = ArticleCategoryName;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getArticleModified() {
            return ArticleModified;
        }

        public String getArticleCategoryID() {
            return ArticleCategoryID;
        }

        public String getContent() {
            return Content;
        }

        public String getArticleTitle() {
            return ArticleTitle;
        }

        public String getAuthor() {
            return Author;
        }

        public String getArticleCreated() {
            return ArticleCreated;
        }

        public String getID() {
            return ID;
        }

        public String getArticleCategoryName() {
            return ArticleCategoryName;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public String getSource() {
            return Source;
        }
    }
}
