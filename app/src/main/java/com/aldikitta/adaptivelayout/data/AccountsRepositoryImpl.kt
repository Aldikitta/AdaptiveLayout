package com.aldikitta.adaptivelayout.data

import com.aldikitta.adaptivelayout.data.local.LocalAccountsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountsRepositoryImpl : AccountsRepository {

    override fun getDefaultUserAccount(): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getDefaultUserAccount())
    }

    override fun getAllUserAccounts(): Flow<List<Account>> = flow {
        emit(LocalAccountsDataProvider.allUserAccounts)
    }

    override fun getContactAccountByUid(uid: Long): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getContactAccountByUid(uid))
    }
}
