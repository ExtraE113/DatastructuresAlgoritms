package ezranewmanCustomDataStructreImplementations

class IntNode(var data: Int) {
    var visited: Int = 0;
    var next: IntNode? = null

    fun countNodesAfter(): Int {
        var count: Int = 1
        var working = this
        while (working.next != null) {
            count++
            working = working.next!!
        }
        return count
    }
    
    fun insertAfter(toInsert: IntNode){
        toInsert.next = next
        next = toInsert
    }

    
//    fun insertLast(toInsert: ezranewmanCustomDataStructreImplementations.IntNode) {
//        var working = this.next
//        while (working?.next != null) {
//            working = working.next!!
//        }
//
//        working!!.next = toInsert
//    }

    override fun toString(): String {
        return "ezranewmanCustomDataStructreImplementations.IntNode(data=$data, next=$next)"
    }

}

fun main() {
    // Code to make the first example list: 4 -> 2 -> null
    // Make the nodes
    val head1 = IntNode(4)
    val A = IntNode(2)
    // Make the connections
    head1.next = A

    // Code to make the second example list: 3 -> 10 -> 2 -> null
    // Make the nodes
    val head2 = IntNode(3)
    val X = IntNode(10)
    val Y = IntNode(2)
    // Make the connections
    head2.next = X
    X.next = Y

    val head3 = IntNode(5)
    val AA = IntNode(6)
    val AB = IntNode(7)
    val AC = IntNode(8)
    val AD = IntNode(9)

    head3.next = AA
    AA.next = AB
    AB.next = AC
    AC.next = AD

    // Test the countNodes function
    println(head1.countNodesAfter()) // 2
    println(head2.countNodesAfter()) // 3
    println(head3.countNodesAfter()) // 5
    println(head2)
    head2.insertAfter(IntNode(102))
    println(head2)
}
