package com.caolancode.authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.caolancode.authentication.R

@Composable
fun AlreadyUser(
    modifier: Modifier,
    question: String,
    navigatePage: () -> Unit,
    linkText: String
) {
    val questionSpacer = dimensionResource(id = R.dimen.sign_in_question_spacer)

    Row {
        Text(text = question)
        Spacer(modifier = modifier.width(questionSpacer))
        Text(
            modifier = modifier.clickable { navigatePage() },
            text = linkText,
            color = Color.Blue
        )
    }
}