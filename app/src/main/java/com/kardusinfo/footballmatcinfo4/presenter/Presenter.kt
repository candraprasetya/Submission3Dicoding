/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballmatcinfo4.presenter

import com.google.gson.Gson
import com.kardusinfo.footballmatcinfo4.api.ApiRepository
import com.kardusinfo.footballmatcinfo4.api.ApiDB
import com.kardusinfo.footballmatcinfo4.model.Event
import com.kardusinfo.footballmatcinfo4.model.EventResponse
import com.kardusinfo.footballmatcinfo4.model.Team
import com.kardusinfo.footballmatcinfo4.model.TeamResonse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Presenter<in T>(private val view: T) {
    private val repository = ApiRepository()
    private val gson = Gson()

    fun getEventDetail(eventId: String?) {
        if (view is EventDetailView) {
            view.showLoading()
            doAsync {
                val data: Event? = gson.fromJson(repository.doRequest(ApiDB.getEventDetail(eventId)),
                        EventResponse::class.java).events[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventDetail(it1) }
                }
            }
        }
    }

    fun getNextMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            doAsync {
                val data: List<Event>? = gson.fromJson(repository.doRequest(ApiDB.getNextMatches(leagueId)),
                        EventResponse::class.java).events

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventList(it1) }
                }
            }
        }
    }

    fun getPrevMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            doAsync {
                val data: List<Event>? = gson.fromJson(repository.doRequest(ApiDB.getPrevMatches(leagueId)),
                        EventResponse::class.java).events

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventList(it1) }
                }
            }
        }
    }


    fun getTeamList(league: String?) {
        if (view is TeamView) {
            view.showLoading()
            doAsync {
                val data: List<Team>? = gson.fromJson(repository.doRequest(ApiDB.getTeams(league)),
                        TeamResonse::class.java
                ).teams

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamList(it1) }
                }
            }
        }
    }

    fun getSepecificTeam(teamName: String?) {
        if (view is TeamDetailView) {
            view.showLoading()
            doAsync {
                val data: Team? = gson.fromJson(repository.doRequest(ApiDB.getSpecificTeam(teamName)),
                        TeamResonse::class.java).teams[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamDetail(it1) }

                }
            }
        }
        if (view is EventDetailView) {
            view.showLoading()
            doAsync {
                val data: Team? = gson.fromJson(repository.doRequest(ApiDB.getSpecificTeam(teamName)),
                        TeamResonse::class.java).teams[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamEmblem(it1) }

                }
            }
        }

    }
}