/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballmatcinfo4.presenter

import com.kardusinfo.footballmatcinfo4.model.Event

interface EventView : BaseView {
    fun showEventList(events: List<Event>)
}