package ir.sweetsoft.sweetlibone.Activities;


public class Constants {
    public static double VERSION_CODE = 1.0;

    public static final String MOBILE = "mobile";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String GENERAL_PREFERENCES="GeneralPrefs";
//    public static final String SITEURL="http://10.0.2.2/";
    public static final String SITEURL="http://ocms.sweetsoft.ir/";
//    public static final String SITEURL="http://192.168.43.97/";
public static final String SIGNUPURL=SITEURL+"json/fa/onlineclass/manageuser.jsp";
    public static final String SIGNINURL=SITEURL+"json/fa/onlineclass/userlist.jsp?service=getuserstatus";
    public static final String PURCHASEURL=SITEURL+"fa/finance/userpayment.jsp";
    public static final String SPECIALITYLIST_URL=SITEURL+"fa/ocms/specialitylist.jsp";
    public static final String MANAGEUSERPLANLIST=SITEURL+"fa/ocms/manageuserdoctorplans.jsp";
    public static final String DOCTORRESERVELIST=SITEURL+"fa/ocms/doctorreservelist.jsp";
    public static final String MANAGEDOCTORS=SITEURL+"fa/ocms/managedoctors.jsp";
    public static final String MANAGESPECIALITIES=SITEURL+"fa/ocms/managespecialitys.jsp";
    public static final String MANAGEVOICES=SITEURL+"fa/fileshop/managefiles.jsp";
    public static final String MANAGETRANSACTIONS=SITEURL+"fa/finance/managetransactions.jsp";
    public static final String LOGIN_URL=SITEURL+"fa/users/asignin.jsp";
//    public static final String PURCHASEURL=SITEURL+"fa/users";

    public static int PRESENCETYPE_INOFFICE=1;
    public static int PRESENCETYPE_INHOME=2;
    public static int PRESENCETYPE_BYTEL=3;


    public static int SPECIALITYGROUP_DOCTOR=1;
    public static int SPECIALITYGROUP_DENTIST=2;
    public static int SPECIALITYGROUP_PSYCHOLOGY=3;
    public static int SPECIALITYGROUP_BEAUTYSHOP=4;
    public static int SPECIALITYGROUP_BEAUTYSHOP_MAN=7;
    public static int SPECIALITYGROUP_BEAUTYSHOP_WOMAN=8;
}
