$("#parseUrl").on("submit", function(e){
    e.preventDefault();
    let urls = $("#exampleFormControlTextarea1").val();
    urls = urls.split("\n").join(",");
    $("#exampleFormControlTextarea1").val(urls);
    document.getElementByld("parseUrl").submit();
});
// Это json который может быть, исходя из структуры. Подразумевается, что мы его откуда-то возьмём
let json = `[
    {"id":2598039,"word":"поиск","code":30459,"codeParent":0,"count":1,"cyrillic":false},
    {"id":2598040,"word":"дерево","code":30460,"codeParent":0,"count":4,"cyrillic":true},
    {"id":2598041,"word":"машина","code":30461,"codeParent":0,"count":5,"cyrillic":false},
    {"id":2598042,"word":"самолёт","code":30462,"codeParent":0,"count":20,"cyrillic":true},
    {"id":2598043,"word":"автомат","code":30463,"codeParent":0,"count":1,"cyrillic":false},
    {"id":2598044,"word":"кабина","code":30464,"codeParent":0,"count":2,"cyrillic":true},
    {"id":2598045,"word":"диван","code":30465,"codeParent":0,"count":67,"cyrillic":false},
    {"id":2598046,"word":"стол","code":30466,"codeParent":0,"count":7,"cyrillic":true},
    {"id":2598047,"word":"стул","code":30467,"codeParent":0,"count":8,"cyrillic":false},
    {"id":2598048,"word":"масло","code":30468,"codeParent":0,"count":11,"cyrillic":true}
]`;

function sortWords(json){
    let words = JSON.parse(json);
    words.forEach((word) => {
        el = word.cyrillic ? ".cyrillic" : ".non-cyrillic";
        $(el).append(`<li class="list-group-item">${word.word} - ${word.count}</li>`);
    })
}
sortWords(json);