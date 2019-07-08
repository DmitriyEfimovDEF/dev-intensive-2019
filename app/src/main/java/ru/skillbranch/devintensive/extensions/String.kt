package ru.skillbranch.devintensive.extensions

fun String.dropEndSpaces():String{
    var toCut:String=this
    var s:String
    val length=this.length
    var cut:Int=0
    var index:Int=length-1
    while(toCut[index]==' '){
        index--
        cut++
    }
    s=toCut.dropLast(cut)
    return s
}

fun String.truncate(number:Int=16, fill:String="..."):String{
    var toCut:String=this
    var s:String

    s=toCut.dropEndSpaces()
    if (s.length>number) {
        s="${s.dropLast(s.length-number)}"
        toCut=s
        s=toCut.dropEndSpaces()
        s+="$fill"
    }
    return s
}

fun String.truncQuote():String{
    var s:String=""
    var quote: Boolean=false
    var i:Int=0;

    for(i in 0 until this.length){
        when (quote){
            false ->{
                if (i + 1 < this.length) {
                    if ((this[i] == '&') && (this[i + 1] != ' ')) quote=true
                    else s+="${this[i]}"
                }
                else s+="${this[i]}"
            }
            true->{
                if(this[i]==' ') {
                    quote = false
                    s += "${this[i]}"
                }
            }
        }
    }
    return s;
}

fun String.clearHtmlTeg():String{
    var s:String=""
    var openTeg: Boolean=false

    this.forEach {
        if(it=='<') openTeg=true
        else if((it=='>')&&(openTeg)) openTeg=false
        else if (!openTeg) s+="$it"
    }
    s=s.truncQuote()
    return s
}

fun String.stripHtml():String{
    val s:String=this.clearHtmlTeg()
    var cleanS:String=""
    var space: Boolean=false

    s.forEach {// Удаляем лишние пробелы
        if((it==' ')&&(!space)){
            cleanS+="$it"
            space=true
        }
        else if(it!=' ') {
            cleanS+="$it"
            space=false
        }
    }

    return cleanS
}