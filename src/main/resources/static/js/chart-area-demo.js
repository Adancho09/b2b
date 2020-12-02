// Set new default font family and font color to mimic Bootstrap's default styling
(Chart.defaults.global.defaultFontFamily = "Metropolis"),
'-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#858796";


function number_format(value) {

    return value;

}


    var datosdev=[];
    $.ajax({
        url : "https://servicios.acteck.com/getDev",
        type : "get",

        success : function(response) {


            datosdev.push(response.enero);
            datosdev.push(response.febrero);
            datosdev.push(response.marzo);
            datosdev.push(response.abril);
            datosdev.push(response.mayo);
            datosdev.push(response.junio);
            datosdev.push(response.julio);
            datosdev.push(response.agosto);
            datosdev.push(response.septiembre);
            datosdev.push(response.octubre);
            datosdev.push(response.noviembre);
            datosdev.push(response.diciembre);
            var datos=[];

            $.ajax({
                url : "https://servicios.acteck.com/getFacturacion",
                type : "get",

                success : function(response) {


                    datos.push(response.enero);
                    datos.push(response.febrero);
                    datos.push(response.marzo);
                    datos.push(response.abril);
                    datos.push(response.mayo);
                    datos.push(response.junio);
                    datos.push(response.julio);
                    datos.push(response.agosto);
                    datos.push(response.septiembre);
                    datos.push(response.octubre);
                    datos.push(response.noviembre);
                    datos.push(response.diciembre);


                    var ctx = document.getElementById("myAreaChart");
                    var myLineChart = new Chart(ctx, {



                        type: "line",
                        data: {
                            labels: [
                                "Jan",
                                "Feb",
                                "Mar",
                                "Apr",
                                "May",
                                "Jun",
                                "Jul",
                                "Aug",
                                "Sep",
                                "Oct",
                                "Nov",
                                "Dec"
                            ],
                            datasets: [{
                                label: "Facturacion",
                                lineTension: 0.3,
                                backgroundColor: "rgba(0, 97, 242, 0.05)",
                                borderColor: "rgba(0, 97, 242, 1)",
                                pointRadius: 3,
                                pointBackgroundColor: "rgba(0, 97, 242, 1)",
                                pointBorderColor: "rgba(0, 97, 242, 1)",
                                pointHoverRadius: 3,
                                pointHoverBackgroundColor: "rgba(0, 97, 242, 1)",
                                pointHoverBorderColor: "rgba(0, 97, 242, 1)",
                                pointHitRadius: 10,
                                pointBorderWidth: 2,
                                data: datos,


                            },
                                {
                                    label: "Devoluciones",
                                    lineTension: 0.3,
                                    backgroundColor: "rgba(203, 50, 52, 0.005)",
                                    borderColor: "rgba(203, 50, 52, 1)",
                                    pointRadius: 3,
                                    pointBackgroundColor: "rgba(203, 50, 52, 1)",
                                    pointBorderColor: "rgba(203, 50, 52, 1)",
                                    pointHoverRadius: 3,
                                    pointHoverBackgroundColor: "rgba(203, 50, 52, 1)",
                                    pointHoverBorderColor: "rgba(203, 50, 52, 1)",
                                    pointHitRadius: 10,
                                    pointBorderWidth: 2,
                                    data: datosdev,

                                }

                            ]
                        },
                        options: {
                            maintainAspectRatio: false,
                            layout: {
                                padding: {
                                    left: 10,
                                    right: 25,
                                    top: 25,
                                    bottom: 0
                                }
                            },
                            scales: {
                                xAxes: [{
                                    time: {
                                        unit: "date"
                                    },
                                    gridLines: {
                                        display: false,
                                        drawBorder: false
                                    },
                                    ticks: {
                                        maxTicksLimit: 7
                                    }
                                }],
                                yAxes: [{
                                    ticks: {
                                        maxTicksLimit: 5,
                                        padding: 10,
                                        // Include a dollar sign in the ticks
                                        callback: function(value, index, values) {
                                            return "$" + number_format(value);
                                        }
                                    },
                                    gridLines: {
                                        color: "rgb(234, 236, 244)",
                                        zeroLineColor: "rgb(234, 236, 244)",
                                        drawBorder: false,
                                        borderDash: [2],
                                        zeroLineBorderDash: [2]
                                    }
                                }]
                            },
                            legend: {
                                display: true
                            },
                            tooltips: {
                                backgroundColor: "rgb(255,255,255)",
                                bodyFontColor: "#858796",
                                titleMarginBottom: 10,
                                titleFontColor: "#6e707e",
                                titleFontSize: 14,
                                borderColor: "#dddfeb",
                                borderWidth: 1,
                                xPadding: 15,
                                yPadding: 15,
                                displayColors: false,
                                intersect: false,
                                mode: "index",
                                caretPadding: 10,
                                callbacks: {
                                    callback:function(tooltipItem, chart) {
                                        var datasetLabel =
                                            chart.datasets[tooltipItem.datasetIndex].label || "";
                                        return datasetLabel + ": $" + number_format(tooltipItem.yLabel);
                                    }
                                }
                            }
                        }
                    });





                },

            });










        },

    });







