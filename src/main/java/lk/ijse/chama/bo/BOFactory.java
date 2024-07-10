package lk.ijse.chama.bo;

import lk.ijse.chama.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBOFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USERBO,DASHBOARDBO,CUSTOMERBO,EMPLOYEEBO,PLACEITEMBO,PLACEORDERBO,REPAIRBO,SUPPLIERBO,TRANSPORTBO,REPORTBO,QUERYBO
    }

    public SuperBO getBO(BOTypes boTypes){

        switch (boTypes){
            case USERBO:
                return new UserBOImpl();
            case DASHBOARDBO:
                return new DashboardBOImpl();
            case PLACEORDERBO:
                return new PlaceOrderBOImpl();
            case EMPLOYEEBO:
                return new EmployeeBOImpl();
            case PLACEITEMBO:
                return new PlaceItemBOImpl();
            case CUSTOMERBO:
                return new CustomerBOImpl();
            case REPAIRBO:
                return new RepairBOImpl();
            case SUPPLIERBO:
                return new SupplierBOImpl();
            case TRANSPORTBO:
                return new TransportBOImpl();
            case REPORTBO:
                return new ReportBOImpl();
            case QUERYBO:
                return new QueryBOImpl();

            default:
                return null;
        }
    }


}
