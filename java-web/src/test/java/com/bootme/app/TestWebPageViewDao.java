
package com.bootme.app;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestWebPageViewDao {

    /*
    @Autowired
    private WebpageViewDao webpageViewDao;

    @Test
    public void testRequestForUrl() throws IOException {

        String url = "http://localhost:8084/eventPageApi?appId=asdfasdf12&date=123";
        String retStr = HttpClientUtil.getUrl(url);
        YoushuEventView eventObj = JSON.parseObject(retStr,YoushuEventView.class);
        Assert.assertEquals(1,eventObj.getResultCode());

        if(webpageViewDao.queryHasRecordData()==0)
        {
            List<YoushuEventView.EventView> list = eventObj.getData();
            for(YoushuEventView.EventView item:list)
            {
                EventView eventView = new EventView();
                eventView.setApp_id("xxxaaa");
                eventView.setTouch_num(item.getTouch_num());
                eventView.setTouch_user_num(item.getTouch_user_num());
                eventView.setCreate_date(new Date());
                webpageViewDao.insertEventView(eventView);
            }

        }



    }

    @Test
    public void testInsertWebpageView() throws InterruptedException {
        for(int i=0;i<5;i++)
        {
            WebpageView webpageView =new WebpageView();
            webpageView.setApp_id("123132"+i);
            webpageView.setCreate_date(new Date());
            webpageView.setVisit_ip_num(12313+i);

            webpageViewDao.insertWebpageView(webpageView);
        }

    }

    @Test
    public void testQueryHowmanyView() throws InterruptedException {
         int ret = webpageViewDao.queryHasRecordData();
        Assert.assertEquals(5,ret);

    }

    @Test
    public void testInsertEventView() throws InterruptedException {
        for(int i=0;i<5;i++)
        {
            EventView eventView =new EventView();
            eventView.setApp_id("123132"+i);
            eventView.setCreate_date(new Date());
            eventView.setTouch_num(12313+i);
            eventView.setTouch_user_num(44413+i);

            webpageViewDao.insertEventView(eventView);
        }

    }

    @Test
    public void selectQueryDao() throws InterruptedException {

            List<WebpageView> list = webpageViewDao.queryListAll();
            for(WebpageView item:list)
            {
                System.out.print("item = "+item.getApp_id());
            }


    }

    */

}
