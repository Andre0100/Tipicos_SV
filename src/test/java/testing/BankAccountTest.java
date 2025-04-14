/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testing;

/**
 *
 * @author morales
 */
//class BankAccountTest {
//
//    private BankAccount account;
//
//    @BeforeAll
//    static void setupAll() {
//        System.out.println("Inicializando recursos globales antes de todas las pruebas...");
//        // Aquí podrías inicializar recursos que se compartirán en todas las pruebas, como conexiones a bases de datos.
//    }
//
//    @BeforeEach
//    void setup() {
//        System.out.println("Inicializando la cuenta antes de cada prueba...");
//        account = new BankAccount(1000); // Cada prueba comienza con una cuenta que tiene 1000 unidades.
//    }
//
//    @Test
//    void testDeposit() {
//        System.out.println("Ejecutando prueba de depósito...");
//        account.deposit(500);
//        assertEquals(1500, account.getBalance(), "El saldo después del depósito debe ser 1500");
//    }
//
//    @Test
//    void testWithdraw() throws InsufficientFundsException {
//        System.out.println("Ejecutando prueba de retiro...");
//        account.withdraw(200);
//        assertEquals(800, account.getBalance(), "El saldo después del retiro debe ser 800");
//    }
//
//    @Test
//    void testInsufficientFunds() {
//        System.out.println("Ejecutando prueba de fondos insuficientes...");
//        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
//            account.withdraw(1500);
//        });
//        assertEquals("Fondos insuficientes", exception.getMessage());
//    }
//
//    @AfterEach
//    void tearDown() {
//        System.out.println("Limpiando después de cada prueba...");
//        // Aquí podrías liberar recursos o reiniciar el estado si es necesario.
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//        System.out.println("Limpiando recursos globales después de todas las pruebas...");
//        // Aquí podrías cerrar conexiones a bases de datos o liberar otros recursos globales.
//    }
//}
