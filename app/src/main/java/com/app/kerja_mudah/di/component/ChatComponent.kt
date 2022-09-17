package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.chat.AllChatRoomActivity
import com.app.kerja_mudah.ui.chat.ChatRoomActivity
import dagger.Subcomponent

@Subcomponent
interface ChatComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():ChatComponent
    }

    fun inject(activity:ChatRoomActivity)
    fun inject(activity:AllChatRoomActivity)
}