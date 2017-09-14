/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
function toggle(source) {
    checkboxes = document.getElementsByName('id');
    for (var i in checkboxes) {
        checkboxes[i].checked = source.checked;
    }

}
