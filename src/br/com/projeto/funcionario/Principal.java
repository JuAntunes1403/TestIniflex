package br.com.projeto.funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    // Definindo uma constante imutável com os funcionários
    private static final List<Funcionario> FUNCIONARIOS_INICIAIS = List.of(
        new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
        new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
        new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
        new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
        new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
        new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
        new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
        new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
        new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
        new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
    );

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(FUNCIONARIOS_INICIAIS);


        // 3.2 - Remover o funcionário “João” da lista
        funcionarios.removeIf(func -> func.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários com todas suas informações
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Funcionario func : funcionarios) {
            System.out.printf("%s - %s - %s - %s%n", func.getNome(),
                    func.getDataNascimento().format(formatter),
                    func.getSalario().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString().replace('.', ','),
                    func.getFuncao());
        }

        // 3.4 - 10% de aumento de salário
        funcionarios.forEach(func -> func.setSalario(func.getSalario().multiply(BigDecimal.valueOf(1.1))));

        // 3.5 - Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários, agrupados por função
        funcionariosPorFuncao.forEach((funcao, funcs) -> {
            System.out.println(funcao + ":");
            funcs.forEach(System.out::println);
        });

        // 3.8 - Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("Aniversariantes do mês 10 e 12:");

        funcionarios.stream()
                .filter(func -> func.getDataNascimento().getMonthValue() == 10 || func.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);

        // 3.9 - Imprimir o funcionário com a maior idade
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        System.out.printf("Funcionário mais velho: %s - Idade: %d%n", maisVelho.getNome(),
                LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear());

        // 3.10 - Imprimir a lista de funcionários por ordem alfabética
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        // 3.11 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Total dos salários: %s%n", totalSalarios.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString().replace('.', ','));

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(func -> {
            BigDecimal salariosMinimos = func.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_EVEN);
            System.out.printf("%s ganha %.2f salários mínimos%n", func.getNome(), salariosMinimos);
        });
    }
}
