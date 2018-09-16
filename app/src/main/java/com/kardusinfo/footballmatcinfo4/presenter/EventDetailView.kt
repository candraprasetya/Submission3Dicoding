/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballmatcinfo4.presenter

import com.kardusinfo.footballmatcinfo4.model.Event
import com.kardusinfo.footballmatcinfo4.model.Team

interface EventDetailView : BaseView {
    fun showEventDetail(event: Event)
    fun showTeamEmblem(team: Team)
}