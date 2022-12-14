package com.aldikitta.adaptivelayout.domain

import com.aldikitta.data.repository.EmailsRepositoryImpl

class GetAllFoldersUseCase(
    private val emailsRepository: EmailsRepositoryImpl
) {
    operator fun invoke(): List<String> {
        return emailsRepository.getAllFolders()
    }
}