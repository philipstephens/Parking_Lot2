package parking

fun main() {
    var command = mutableListOf<String>("x", "x", "x", "x")
    var input: String?
    val parkingLot = ParkingLot()

    while(true) {
        input = readLine() ?: error("No lines read")
        command = input.split(" ").toMutableList()
        when (command[0]) {
            "create" -> parkingLot.create(command[1].toInt())
            "park"   -> parkingLot.park(command[1], command[2])
            "leave"  -> parkingLot.leave(command[1].toInt())
            "status" -> parkingLot.status()
            "reg_by_color" -> parkingLot.regByColor(command[1])
            "spot_by_color" -> parkingLot.spotByColor(command[1])
            "spot_by_reg" -> parkingLot.spotByReg(command[1])
            "exit" -> break
        }
    }
}

class Car() {
    var registration: String = ""
    var colour: String = ""

    fun create(_registration: String, _colour: String) {
        this.registration = _registration
        this.colour = _colour
    }
}

class Spot(val _isOccupied: Boolean = false, val _car: Car = Car()) {
    var isOccupied = _isOccupied
    var car = _car
}

class ParkingLot() {
    var spots: MutableList<Spot> = mutableListOf<Spot>()

    fun create(numSpots: Int) {
        if (numSpots < 1) {
            println("number of spots must be at least 1!")
            return
        } else {
            if (spots.isNotEmpty()) {
                spots.clear()
            }
        }

        repeat(numSpots) {
            spots.add(Spot())
        }

        println("Created a parking lot with $numSpots spots.")
    }

    fun park(_registration: String, _colour: String) {
        var parked = false
        var stall = 0

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        for (i in 0..spots.lastIndex) {
            if (!spots[i].isOccupied) {
                spots[i].isOccupied = true
                parked = true
                spots[i].car.create(_registration, _colour)
                stall = i + 1
                break
            }
        }

        if (!parked ) {
            println("Sorry, the parking lot is full.")
        } else {
            println("$_colour car parked in spot ${stall}.")
        }
    }

    fun leave(spotNumber: Int) {
        val x = spotNumber - 1

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

//    if(spotNumber < 1 || spotNumber > spots.size) {
//        println("Spot number $spotNumber doesn't exist!")
//        return
//    }

        if(!spots[x].isOccupied) {
            println("There is no car in spot ${spotNumber}.")
        } else {
            spots[x].isOccupied = false
            println("Spot $spotNumber is free.")
        }
    }

    fun status() {
        var lotEmpty = true

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        for (i in 0..spots.lastIndex) {
            if(spots[i].isOccupied) {
                lotEmpty = false
                println("${i+1} ${spots[i].car.registration} ${spots[i].car.colour}")
            }
        }

        if(lotEmpty) {
            println("Parking lot is empty.")
        }
    }

    fun spotByReg(regNumber: String) {
        val searchReg = regNumber.uppercase()
        var currentReg: String
        var foundReg = false

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        for (i in 0..spots.lastIndex) {
            if (spots[i].isOccupied) {
                currentReg = spots[i].car.registration.uppercase()
                if(currentReg == searchReg) {
                    foundReg = true
                    println(i + 1)
                    break
                }
            }
        }

        if(!foundReg) {
            println("No cars with registration number $searchReg were found.")
        }
    }

    fun spotByColor(colour: String) {
        val searchColour = colour.uppercase()
        var currentColour: String
        var foundColour = false
        var pFlag = false

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        for (i in 0..spots.lastIndex) {
            if (spots[i].isOccupied) {
                currentColour = spots[i].car.colour.uppercase()
                if(currentColour == searchColour) {
                    foundColour = true
                    if (!pFlag) {
                        pFlag = true
                        print("${i+1}")
                    } else {
                        print(", ${i+1}")
                    }
                }
            }
        }

        if(!foundColour) {
            println("No cars with color $searchColour were found.")
        } else {
            println()
        }
    }

    fun regByColor(colour: String) {
        val searchColour = colour.uppercase()
        var currentColour: String
        var foundColour = false
        var pFlag = false

        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        for (i in 0..spots.lastIndex) {
            if (spots[i].isOccupied) {
                currentColour = spots[i].car.colour.uppercase()
                if(currentColour == searchColour) {
                    foundColour = true
                    if (!pFlag) {
                        pFlag = true
                        print(spots[i].car.registration)
                    } else {
                        print(", ${spots[i].car.registration}")
                    }
                }
            }
        }

        if(!foundColour) {
            println("No cars with color $searchColour were found.")
        } else {
            println()
        }
    }
}