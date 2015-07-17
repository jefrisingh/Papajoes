package www.thetunagroup.com.papajoes;

/**
 * Created by DELL-PC on 7/6/2015.
 */
public class AppUrls {
    public static String loginUrl="http://papajoe.thetunagroup.com/papajoe/api/login";  // username(String:email) and password(String)
    public static String incommingOrders="http://papajoe.thetunagroup.com/papajoe/api/get/incoming/orders";  // user_id (2)
    public static String pendingOrders="http://papajoe.thetunagroup.com/papajoe/api/get/pending/orders";  // user_id (2)
    public static String completedOrders="http://papajoe.thetunagroup.com/papajoe/api/get/completed/orders";  // user_id (2)
    public static String deliveredOrders="http://papajoe.thetunagroup.com/papajoe/api/get/delivered/orders";  // user_id (2)
    public static String getOrdersItems="http://papajoe.thetunagroup.com/papajoe/api/get/order/items";//user_id  //order_id
    public static String changeOrderStatus="http://papajoe.thetunagroup.com/papajoe/api/order/changeStatus";// user_id  order_id type(2)
}
