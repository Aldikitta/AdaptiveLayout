package com.aldikitta.adaptivelayout.domain

import com.aldikitta.data.model.Email
import com.aldikitta.data.model.MailboxType
import com.aldikitta.data.repository.EmailsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetCategoryEmailsUseCase(
    private val emailsRepository: EmailsRepositoryImpl
) {
    operator fun invoke(category: MailboxType): Flow<List<Email>>{
        return emailsRepository.getCategoryEmails(category)
    }
}