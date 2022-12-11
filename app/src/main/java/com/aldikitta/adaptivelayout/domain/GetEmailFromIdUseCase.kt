package com.aldikitta.adaptivelayout.domain

import com.aldikitta.adaptivelayout.data.model.Email
import com.aldikitta.adaptivelayout.data.repository.EmailsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetEmailFromIdUseCase(
    private val emailsRepository: EmailsRepositoryImpl
) {
    operator fun invoke(id: Long): Flow<Email?> {
        return emailsRepository.getEmailFromId(id)
    }
}