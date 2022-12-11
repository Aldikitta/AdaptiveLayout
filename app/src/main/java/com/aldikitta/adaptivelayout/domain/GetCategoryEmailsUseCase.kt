package com.aldikitta.adaptivelayout.domain

import com.aldikitta.adaptivelayout.data.model.Email
import com.aldikitta.adaptivelayout.data.model.MailboxType
import com.aldikitta.adaptivelayout.data.repository.EmailsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetCategoryEmailsUseCase(
    private val emailsRepository: EmailsRepositoryImpl
) {
    operator fun invoke(category: MailboxType): Flow<List<Email>>{
        return emailsRepository.getCategoryEmails(category)
    }
}