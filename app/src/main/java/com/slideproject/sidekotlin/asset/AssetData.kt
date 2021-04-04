package com.slideproject.sidekotlin.asset

class AssetData {
    var rank: String? = null
    var name: String? = null
    var asset: String? = null
    var company: String? = null

    constructor() {}

    constructor(rank: String?, name: String?, asset: String?, company: String?) {
        this.rank = rank
        this.name = name
        this.asset = asset
        this.company = company
    }
}