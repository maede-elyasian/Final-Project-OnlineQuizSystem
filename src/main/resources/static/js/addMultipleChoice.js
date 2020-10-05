var number = 0;

function addChoice() {
    var ddl = document.getElementById("choices");
    var option = document.createElement("OPTION");
    option.innerHTML = document.getElementById("choiceContent").value;
    ddl.options.add(option);
    number++;
    console.log(number);
}

function addHide() {
    var all = "";
    var allChoices = document.getElementById("choices").options;
    for (var i = 0; i < number; i++)
        all += allChoices[i].value + ',';
    document.getElementById('myChoice').value = all;
    console.log(all);
}

function removeChoice() {
    var x = document.getElementById("choices");
    x.remove(x.selectedIndex);
}