package com.bootme.app.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;


/**
 * 用来进行超过N次就进行锁定的登录处理
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {



    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        // retry count + 1
   /*     AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
  //          passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            // if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }
*/
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // clear retry count
//            passwordRetryCache.remove(username);
        }
        return matches;
    }
}