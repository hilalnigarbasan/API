package post_requests;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Post04 extends HerOkuAppBaseUrl {
    /*
        Given
         1)  https://restful-booker.herokuapp.com/booking
         2)   {
               "firstname": "Ali",
               "lastname": "Can",
               "totalprice": 999,
               "depositpaid": true,
               "bookingdates": {
                   "checkin": "2021-09-21",
                   "checkout": "2021-12-21"
                },
                "additionalneeds": "Breakfast"
            }
       When
            I send POST Request to the URL
        Then
            Status code is 200
        And
            Response body is like {
                                    "bookingid": 16,
                                    "booking" :{
                                       "firstname": "Ali",
                                       "lastname": "Can",
                                       "totalprice": 999,
                                       "depositpaid": true,
                                       "bookingdates": {
                                           "checkin": "2021-09-21",
                                           "checkout": "2021-12-21"
                                       },
                                       "additionalneeds": "Breakfast"
                                    }
                                 }
    */
    @Test
    public void post04(){
//        i)   Set the URL
        spec.pathParam("first","booking");

//        ii)  Set the expected data
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2021-09-21","2021-12-21");
        BookingPojo expectedData = new BookingPojo("Ali","Can",999,true,bookingDatesPojo,"Breakfast");
        System.out.println("expectedData = " + expectedData);

//        iii) Send the request and get the response
        Response response = given().spec(spec).when().body(expectedData).post("{first}");
        response.prettyPrint();

//        iv)  Do assertion
        BookingResponsePojo actualData = response.as(BookingResponsePojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200,response.statusCode());
        assertEquals(expectedData.getFirstname(), actualData.getBooking().getFirstname());
        assertEquals(expectedData.getLastname(), actualData.getBooking().getLastname());
        assertEquals(expectedData.getTotalprice(), actualData.getBooking().getTotalprice());
        assertEquals(expectedData.getDepositpaid(), actualData.getBooking().getDepositpaid());
        assertEquals(bookingDatesPojo.getCheckin(), actualData.getBooking().getBookingdates().getCheckin());
        assertEquals(bookingDatesPojo.getCheckout(), actualData.getBooking().getBookingdates().getCheckout());
        assertEquals(expectedData.getAdditionalneeds(),actualData.getBooking().getAdditionalneeds());

    }
}