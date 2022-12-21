package com.aldikitta.data.repository

import com.aldikitta.data.model.Email
import com.aldikitta.data.model.MailboxType
import com.aldikitta.data.local.email.LocalEmailsDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EmailsRepositoryImpl : EmailsRepository {

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
