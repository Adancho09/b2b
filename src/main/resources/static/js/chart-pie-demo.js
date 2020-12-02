// Set new default font family and font color to mimic Bootstrap's default styling
(Chart.defaults.global.defaultFontFamily = "Metropolis"),
'-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#858796";

// Pie Chart Example
var ctx = document.getElementById("myPieChart");

var myPieChart = new Chart(ctx, {
    type: "doughnut",
    data: {
        labels: ["LIMITE", "SALDO", "DISPONIBLE"],
        datasets: [{

            data: [55, 30, 15],
            backgroundColor: [
                "rgba(59, 131, 189,1)",
                "rgba(248, 243, 105, 1)",
                "rgba(189, 236, 182, 1)"
            ],
            hoverBackgroundColor: [
                "rgba(59, 131, 189,1)",
                "rgba(248, 243, 105, 1)",
                "rgba(189, 236, 182, 1)"
            ],
            hoverBorderColor: "rgba(234, 236, 244, 1)"
        }]
    },

    options: {
        maintainAspectRatio: false,
        tooltips: {
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: "#dddfeb",
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 50
        },
        legend: {
            display: true
        },
        cutoutPercentage: 30
    }
});
