const searchForm = document.getElementById("searchForm");
const searchFormInput = document.getElementById("searchForm:inputSearch");
const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
const recognition = new SpeechRecognition();
recognition.continuous = true;

const micBtn = searchForm.querySelector("button");
const micIcon = micBtn.firstElementChild;

micBtn.addEventListener("click", micBtnClick);
function micBtnClick() {
    if (micIcon.classList.contains("fa-microphone")) {
        recognition.start();
    } else {
        recognition.stop();
    }
}

recognition.addEventListener("start", startSpeechRecognition);
function startSpeechRecognition() {
    micIcon.classList.remove("fa-microphone");
    micIcon.classList.add("fa-microphone-slash");
    searchFormInput.value = "";
    searchFormInput.focus();
    console.log("Voice activated, SPEAK");
}

recognition.addEventListener("end", endSpeechRecognition);
function endSpeechRecognition() {
    micIcon.classList.remove("fa-microphone-slash");
    micIcon.classList.add("fa-microphone");
    searchFormInput.focus();
    console.log("Speech recognition service disconnected");
}

var searchKeywords;
recognition.addEventListener("result", resultOfSpeechRecognition);
function resultOfSpeechRecognition(event) {
    const current = event.resultIndex;
    const transcript = event.results[current][0].transcript;

    if (transcript.toLowerCase().trim() === "stop recording") {
        recognition.stop();
    } else if (!searchFormInput.value) {
        searchFormInput.value = transcript;
        searchKeywords = searchFormInput.value;
        $('#searchForm\\:btnSearch').click();
        $("#searchForm\\:inputSearch").focus();
    } else {
        if (transcript.toLowerCase().trim() === "go") {
            $('#searchForm\\:viewAllLink').click();
        } else if (transcript.toLowerCase().trim() === "reset input") {
            searchFormInput.value = "";
        } else {
            searchKeywords = searchKeywords + transcript;
            console.log(searchKeywords);
            searchFormInput.value = searchKeywords;
            $('#searchForm\\:btnSearch').click();
            $("#searchForm\\:inputSearch").focus();
        }
    }
    // searchFormInput.value = transcript;
    // searchFormInput.focus();
    // setTimeout(() => {
    //   searchForm.submit();
    // }, 500);
}
