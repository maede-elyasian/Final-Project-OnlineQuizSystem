function deleteConfirm(id) {
    if (document.getElementById(id).hidden == false)
        document.getElementById(id).hidden = true;
    else
        document.getElementById(id).hidden = false;
}
