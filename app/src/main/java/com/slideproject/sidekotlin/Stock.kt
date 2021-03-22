package com.slideproject.sidekotlin

class Stock {
    var name:String? = null
    var deal:String? = null
    var quoteChange:String? = null
    var increase:String? = null

    constructor(name: String, deal: String, quoteChange: String, increase: String) {
        this.name = name
        this.deal = deal
        this.quoteChange = quoteChange
        this.increase = increase
    }
}