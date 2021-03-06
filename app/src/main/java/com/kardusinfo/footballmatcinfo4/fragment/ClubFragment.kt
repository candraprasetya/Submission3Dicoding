package com.kardusinfo.footballmatcinfo4.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kardusinfo.footballmatcinfo4.R
import com.kardusinfo.footballmatcinfo4.activity.ClubInfoActivity
import com.kardusinfo.footballmatcinfo4.adapter.ClubAdapter
import com.kardusinfo.footballmatcinfo4.model.Team
import com.kardusinfo.footballmatcinfo4.presenter.Presenter
import com.kardusinfo.footballmatcinfo4.presenter.TeamView
import kotlinx.android.synthetic.main.fragment_club.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh


class ClubFragment : Fragment(), TeamView {

    private lateinit var adapter: ClubAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private var teamList: MutableList<Team> = mutableListOf()
    private var leagueList: MutableList<String> = mutableListOf()
    private lateinit var swRefresh: SwipeRefreshLayout
    private lateinit var presenter: Presenter<TeamView>
    private var leagueName = "English Premiere league" //default league

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueList.addAll(resources.getStringArray(R.array.league))
        presenter = Presenter(this)
        spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, leagueList)
        adapter = ClubAdapter(ctx, teamList) {
            ctx.startActivity(intentFor<ClubInfoActivity>("team" to it.teamName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club, container, false)
        swRefresh = view.swipeRefresh
        swRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )
        swRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
        view.spinnerLeague.adapter = spinnerAdapter
        view.spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinnerAdapter.getItem(position).toString()
                presenter.getTeamList(leagueName)
            }

        }
        view.rvTeams.adapter = adapter
        view.rvTeams.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        presenter.getTeamList(leagueName)
        return view
    }


    override fun showLoading() {
        swRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swRefresh.isRefreshing = false
    }


    override fun showTeamList(teams: List<Team>) {
        teamList.clear()
        teamList.addAll(teams)
        adapter.notifyDataSetChanged()
    }


}
