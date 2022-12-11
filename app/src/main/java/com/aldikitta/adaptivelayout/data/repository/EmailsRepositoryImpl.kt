package com.aldikitta.adaptivelayout.data.repository

import com.aldikitta.adaptivelayout.data.model.Email
import com.aldikitta.adaptivelayout.data.model.MailboxType
import com.aldikitta.adaptivelayout.data.local.email.LocalEmailsDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EmailsRepositoryImpl : EmailsRepository {

//    override fun getAllEmails(): Flow<List<Email>> = flow {
//        emit(LocalEmailsDataProvider.allEmails)
//    }
//
//    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
//        val categoryEmails = LocalEmailsDataProvider.allEmails.filter { it.mailbox == category }
//        emit(categoryEmails)
//    }
//
//    override fun getAllFolders(): List<String> {
//        return LocalEmailsDataProvider.getAllFolders()
//    }
//
//    override fun getEmailFromId(id: Long): Flow<Email?> = flow {
//        val categoryEmails = LocalEmailsDataProvider.allEmails.firstOrNull { it.id == id }
//    }


    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }.flowOn(Dispatchers.Main)

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> {
        return getAllEmails().map {
            it.filter { email ->
                email.mailbox == category
            }
        }
    }

    override fun getAllFolders(): List<String> {
        return LocalEmailsDataProvider.getAllFolders()
    }

    override fun getEmailFromId(id: Long): Flow<Email?> {
        return getAllEmails().map {
            it.firstOrNull { email ->
                email.id == id
            }
        }
    }
}
