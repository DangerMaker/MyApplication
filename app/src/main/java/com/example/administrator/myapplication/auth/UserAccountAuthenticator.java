package com.example.administrator.myapplication.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.myapplication.ui.LoginActivity;


public class UserAccountAuthenticator extends AbstractAccountAuthenticator {
    private String _tag = "UserAccountAuthenticator";
    private Context _context;


    public UserAccountAuthenticator(Context context) {
        super(context);
        _context = context;
    }

    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {
        Bundle ret = new Bundle();

        Intent intent = new Intent(_context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        ret.putParcelable(AccountManager.KEY_INTENT, intent);
        return ret;
    }


    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
        return null;
    }


    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }


    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) throws NetworkErrorException {
        return null;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }


    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }


    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) {
        return null;
    }

}
