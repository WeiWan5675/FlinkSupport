
/**
 * @Author: xiaozhennan
 * @Date: 2020/11/3 13:48
 * @Package: PACKAGE_NAME.TestEnter
 * @ClassName: TestEnter
 * @Description:
 **/
public class TestEnter {

    public static void main(String[] args) throws Exception {
        args = new String[5];
        args[0] = "-logLevel";
        args[1] = "INFO";
        args[2] = "-x";
        args[3] = "-jobConf";
        args[4] = "eyJldGwucHJvY2Vzc2VyLm5hbWUiOiJleGFtcGxlY2hhbm5sZSIsImV0bC5wcm9jZXNzZXIucGFyYWxsZWxpc20iOiIxIiwiZmxpbmsudGFzay5jb21tb24ucGFyYWxsZWxpc20iOiIxIiwiaGFkb29wQ29uZmlndXJhdGlvbiI6eyJmaW5hbFBhcmFtZXRlcnMiOlsibWFwcmVkdWNlLmpvYi5lbmQtbm90aWZpY2F0aW9uLm1heC5yZXRyeS5pbnRlcnZhbCIsImhhZG9vcC5zc2wucmVxdWlyZS5jbGllbnQuY2VydCIsImhhZG9vcC5zc2wuY2xpZW50LmNvbmYiLCJoYWRvb3Auc3NsLnNlcnZlci5jb25mIiwibWFwcmVkdWNlLmpvYi5lbmQtbm90aWZpY2F0aW9uLm1heC5hdHRlbXB0cyIsImhhZG9vcC5zc2wua2V5c3RvcmVzLmZhY3RvcnkuY2xhc3MiXX0sImV0bC53cml0ZXIuYmF0Y2hXcml0ZVNpemUiOiIxMDAiLCJmbGluay50YXNrLmNoZWNrcG9pbnQubW9kZSI6IkVYQUNUTFlfT05DRSIsIkhBRE9PUF9VU0VSX05BTUUiOiJlYXN5bGlmZSIsImZsaW5rLnRhc2suY29tbW9uLnJlc3RhcnRNb2RlIjoibm9uZSIsImZsaW5rLnRhc2suY2hlY2twb2ludC5pbnRlcnZhbCI6IjYwMDAwIiwiRVRMX1BMVUdJTl9XUklURVJfRElSIjoicGx1Z2lucy93cml0ZXIiLCJldGwucmVhZGVyLm5hbWUiOiJleGFtcGxlcmVhZGVyIiwiU0NBTEFfVkVSU0lPTiI6IjIuMTEiLCJmbGluay50YXNrLmNoZWNrcG9pbnQuZXh0ZXJuYWxpemVkLmVuYWJsZSI6ImZhbHNlIiwiZXRsLnByb2Nlc3Nlci5leGFtcGxlLmNoYW5uZWxWYXIiOiJjaGFubmVsX3ZhciIsImZsaW5rLnRhc2suY29tbW9uLnJlc3RhcnRGYWlsTWF4TnVtIjoiMSIsIkVUTF9QTFVHSU5fUkVBREVSX0RJUiI6InBsdWdpbnMvcmVhZGVyIiwiZXRsLnJlYWRlci5leGFtcGxlLmVuZEluZGV4IjoiMTAwMCIsImZsaW5rLnRhc2sudHlwZSI6ImV0bCIsImFwcC5hcHBDbGFzcyI6ImNvbS53ZWl3YW4udGVzdC5lYXN5bGlmZS5UZXN0QXBwIiwiZmxpbmsudGFzay5zdGF0ZUJhY2tlbmQudHlwZSI6Ik1lbW9yeSIsImZsaW5rQ29uZmlndXJhdGlvbiI6e30sImZsaW5rLnRhc2suY2hlY2twb2ludC5lbmFibGUiOiJmYWxzZSIsImV0bC53cml0ZXIucGFyYWxsZWxpc20iOiIxIiwiRVRMX1BMVUdJTl9DSEFOTkVMX0RJUiI6InBsdWdpbnMvY2hhbm5lbCIsImZsaW5rLnRhc2suY2hlY2twb2ludC5leHRlcm5hbGl6ZWQuY2xlYW5VcCI6IkRFTEVURV9PTl9DQU5DRUxMQVRJT04iLCJhcHAuYXBwTmFtZSI6IlRlc3RBcHAiLCJldGwud3JpdGVyLmJhdGNoV3JpdGVNb2RlIjoiZmFsc2UiLCJGTElOS19IT01FIjoiL2hvbWUvZWFzeWxpZmUvYXBwcy9mbGluay0xLjExLjEiLCJmbGluay50YXNrLmNvbW1vbi5yZXN0YXJ0TnVtIjoiMSIsImZsaW5rLnRhc2suY2hlY2twb2ludC50aW1lb3V0IjoiNjAwMDAiLCJmbGluay50YXNrLnN0YXRlQmFja2VuZC5hc3luYyI6InRydWUiLCJmbGluay50YXNrLm5hbWUiOiJUZXN0QXBwIiwiZmxpbmsudGFzay5jaGVja3BvaW50Lm1heENvbmN1cnJlbnQiOiIxIiwiZXRsLndyaXRlci5uYW1lIjoiZXhhbXBsZXdyaXRlciIsImZsaW5rLnRhc2suY29tbW9uLnJlc3RhcnRJbnRlcnZhbCI6IjMwMDAwIiwiZXRsLnJlYWRlci5wYXJhbGxlbGlzbSI6IjEiLCJFVExfUExVR0lOX0RJUiI6InBsdWdpbnMiLCJ5YXJuQ29uZmlndXJhdGlvbiI6eyJmaW5hbFBhcmFtZXRlcnMiOltdfSwiZmxpbmsudGFzay5jaGVja3BvaW50Lm1pbkludGVydmFsIjoiNTAwIiwiYXBwLmFwcFR5cGUiOiJzdHJlYW0iLCJIQURPT1BfSE9NRSI6Ii9vcHQvY2xvdWRlcmEvcGFyY2Vscy9DREgvbGliL2hhZG9vcCIsImZsaW5rLnRhc2suY29tbW9uLnF1ZXVlIjoicm9vdC51c2Vycy5lYXN5bGlmZSIsIkZMSU5LX1ZFUlNJT04iOiIxLjExLjEiLCJmbGluay50YXNrLmNoZWNrcG9pbnQub25GYWlsIjoidHJ1ZSIsImZsaW5rLmFwcC5hcHBOYW1lIjoiU3VwcG9ydCBBcHBsaWNhdGlvbiIsImV0bC53cml0ZXIuZXhhbXBsZS53cml0ZXJWYXIiOiJ3cml0ZXJfdmFyIn0=";



    }

}