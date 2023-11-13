package com.kyawzinlinn.uidesign.datasource

import com.kyawzinlinn.uidesign.R
import com.kyawzinlinn.uidesign.model.BottomNavigationItem
import com.kyawzinlinn.uidesign.model.Event
import com.kyawzinlinn.uidesign.model.UpcomingShow

object DataSource {
    val images = listOf(R.drawable.aquarium_1, R.drawable.aquarium_2, R.drawable.aquarium_3)

    val events = listOf(
        Event("My e-tickets","They aren't any yet.","Retrieve here",R.drawable.icons_v2),
        Event("Park hours","Today, 13 Feb 10am - 5pm","Plan my visit",R.drawable.icons_v2_2),
    )

    val bottomNavigationItems = listOf(
        BottomNavigationItem(R.drawable.on,"Home"),
        BottomNavigationItem(R.drawable.on_2,"Wallet"),
        BottomNavigationItem(R.drawable.on_2,"More"),
    )

    val upcomingShows = listOf(
        UpcomingShow("2:30 PM","Dive Feeding @Shipwreck",R.drawable.aquarium_1),
        UpcomingShow("2:40 PM","Dive Feeding @Shipwreck",R.drawable.aquarium_2),
    )
}