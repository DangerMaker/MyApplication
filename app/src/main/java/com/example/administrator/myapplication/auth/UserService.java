package com.example.administrator.myapplication.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.UserData;
import com.example.administrator.myapplication.util.SharedPreferencesHelper;

/**
 * Created by Administrator on 2016/4/14.
 */
public class UserService {

    private final String KEY_DEFAULT_ACCOUNT = "defaultAccount";
    private final String KEY_USER_DATA_USER_ID = "uid";
    private final String KEY_USER_DATA_USER_NAME = "name";
    private final String KEY_USER_DATA_USER_MAIL = "mail";
    private final String KEY_USER_DATA_USER_THEME = "theme";
    private final String KEY_USER_DATA_USER_SIGNATURE = "signature";
    private final String KEY_USER_DATA_USER_SIGNATURE_FORMAT = "signature_format";
    private final String KEY_USER_DATA_USER_CREATED = "created";
    private final String KEY_USER_DATA_USER_ACCESS = "access";
    private final String KEY_USER_DATA_USER_LOGIN = "login";
    private final String KEY_USER_DATA_USER_STATUS = "status";
    private final String KEY_USER_DATA_USER_TIMEZONE = "timezone";
    private final String KEY_USER_DATA_USER_LANGUAGE = "language";
    private final String KEY_USER_DATA_USER_PICTURE = "picture";
    private final String KEY_USER_DATA_USER_INIT = "init";
    private final String KEY_USER_DATA_USER_DATA = "data";

    private static UserService mInstance;
    private Account activeAccount;
    private SharedPreferencesHelper sharedPrefs;
    private AccountManager mAccountManager;
    private Context mContext;

    public UserService(Context c) {
        mContext = c;
        sharedPrefs = new SharedPreferencesHelper(c);
        mAccountManager = AccountManager.get(c);
        if (hasDefaultAccount()) {
            activeAccount = getDefaultAccount();
        }
        if (getActiveAccountInfo().getUid() == null) {
            MyApplication.userID = "0";
        } else {
            MyApplication.userID = getActiveAccountInfo().getUid();
        }
    }

    public static synchronized UserService getInstance(Context c) {
        if (mInstance == null)
            mInstance = new UserService(c.getApplicationContext());
        return mInstance;
    }

    private boolean hasDefaultAccount() {
        return getDefaultAccount() != null;
    }

    private Account getDefaultAccount() {
        String accountName = sharedPrefs.getValue(KEY_DEFAULT_ACCOUNT);
        return findAccountByUsername(accountName);
    }

    private void setDefaultAccount(Account defaultAccount) {
        sharedPrefs.setValue(KEY_DEFAULT_ACCOUNT, defaultAccount.name);

        boolean foundAccount = false;
        Account[] accounts = mAccountManager.getAccounts();
        for (int i = 0; i < accounts.length && !foundAccount; i++) {
            if (accounts[i].name.equals(defaultAccount.name)) {
                foundAccount = true;
            }
        }

        if (!foundAccount) {
            // 默认账户被注销了?
        }
    }

    public boolean hasActiveAccount() {
        return this.activeAccount != null;
    }

    public UserData getActiveAccountInfo() {
        if (null != activeAccount)
            return getAccountInfo(activeAccount);
        else
            return new UserData();
    }

    public Account getActiveAccount() {
        return this.activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account setActiveAccount(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            account = new Account(username, mContext.getString(R.string.ACCOUNT_TYPE));
            mAccountManager.addAccountExplicitly(account, password, null);
        }
        this.activeAccount = account;
        return account;
    }

    public String getPassword(Account account) {
        return mAccountManager.getPassword(account);
    }

    public String getPassword() {
        return mAccountManager.getPassword(getActiveAccount());
    }

    public void setPassword(String pass){
        mAccountManager.setPassword(activeAccount,pass);
    }

    public boolean signIn(String username, String password, UserData user) {
        if (user == null) {
            return false;
        }
        Account account = setActiveAccount(username, password);
        setDefaultAccount(account);
        updateAccountInfo(account, user);
        return true;
    }

    private UserData getAccountInfo(Account account) {
        UserData user = new UserData();
        user.setUid(mAccountManager.getUserData(account, KEY_USER_DATA_USER_ID));
        user.setName(mAccountManager.getUserData(account, KEY_USER_DATA_USER_NAME));
        user.setMail(mAccountManager.getUserData(account, KEY_USER_DATA_USER_MAIL));
        user.setTheme(mAccountManager.getUserData(account, KEY_USER_DATA_USER_THEME));
        user.setSignature(mAccountManager.getUserData(account, KEY_USER_DATA_USER_SIGNATURE));
        user.setSignature_format(mAccountManager.getUserData(account, KEY_USER_DATA_USER_SIGNATURE_FORMAT));
        user.setCreated(mAccountManager.getUserData(account, KEY_USER_DATA_USER_CREATED));
        user.setAccess(mAccountManager.getUserData(account, KEY_USER_DATA_USER_ACCESS));
        user.setLogin(mAccountManager.getUserData(account, KEY_USER_DATA_USER_LOGIN));
        user.setStatus(mAccountManager.getUserData(account, KEY_USER_DATA_USER_STATUS));
        user.setTimezone(mAccountManager.getUserData(account, KEY_USER_DATA_USER_TIMEZONE));
        user.setLanguage(mAccountManager.getUserData(account, KEY_USER_DATA_USER_LANGUAGE));
        user.setPicture(mAccountManager.getUserData(account, KEY_USER_DATA_USER_PICTURE));
        user.setInit(mAccountManager.getUserData(account, KEY_USER_DATA_USER_INIT));
        user.setData(mAccountManager.getUserData(account, KEY_USER_DATA_USER_DATA));
        return user;
    }

    public void updateActiveAccountInfo(UserData userProfile) {
        updateAccountInfo(activeAccount, userProfile);
    }

    public void updateAccountInfo(Account account, UserData userProfile) {
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_ID, userProfile.getUid());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_NAME, userProfile.getName());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_MAIL, userProfile.getMail());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_THEME, userProfile.getTheme());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_SIGNATURE,userProfile.getSignature());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_SIGNATURE_FORMAT, userProfile.getSignature_format());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_CREATED,userProfile.getCreated());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_ACCESS, userProfile.getAccess());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_LOGIN, userProfile.getLogin());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_STATUS, userProfile.getStatus());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_TIMEZONE, userProfile.getTimezone());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_LANGUAGE, userProfile.getLanguage());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_PICTURE, userProfile.getPicture());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_INIT, userProfile.getInit());
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_DATA, userProfile.getData());
    }

    private Account findAccountByUsername(String username) {
        Log.d("tab", "findAccountByUsername, username: " + username);
        if (username.length() > 0) {
            for (Account account : mAccountManager.getAccountsByType(mContext.getString(R.string.ACCOUNT_TYPE))) {
                Log.d("tab", "findAccountByUsername, a.name: " + account.name);
                if (account.name.equals(username)) {
                    return account;
                }
            }
        }
        MyApplication.userID = "0";
        return null;
    }

    public void logout(String username) {
        Account account = findAccountByUsername(username);
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            sharedPrefs.remove(KEY_DEFAULT_ACCOUNT);
        }
    }

    public void logout() {
        Account account = getDefaultAccount();
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            sharedPrefs.remove(KEY_DEFAULT_ACCOUNT);
            activeAccount = null;
        }
        MyApplication.userID = "0";
    }

//    public void updateAccountAvatar(String avatar) {
//        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_AVATAR, avatar);
//    }
//
//    public void updateAccountGender(String gender) {
//        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_GENDER, gender);
//    }

    public void updateAccoutMail(String mail){
        mAccountManager.setUserData(activeAccount,KEY_USER_DATA_USER_MAIL,mail);
    }

}
