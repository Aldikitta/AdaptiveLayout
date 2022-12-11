package com.aldikitta.adaptivelayout.domain

import com.aldikitta.adaptivelayout.data.model.Email
import com.aldikitta.adaptivelayout.data.repository.EmailsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetAllEmailsUseCase(
    private val emailsRepository: EmailsRepositoryImpl
) {
    operator fun invoke(): Flow<List<Email>> {
        return emailsRepository.getAllEmails()
    }
}