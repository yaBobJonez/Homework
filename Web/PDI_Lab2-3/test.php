<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8"/>
    <title> Перевірка усного рахунку.</title>
    <link rel="stylesheet" href="main.css"/>
    <script>
        let clear = true;
        function input_sign(x) {
            if (clear) window.document.forms["test"].result.value = "";
            clear = false;
            window.document.forms["test"].result.value += x;
        }
    </script>
</head>
<body>
<?php
    session_start();

    $max_value = $_SESSION["max_value"] ?? 10;
    $sign      = $_SESSION["sign"]      ?? '+';
    $op1       = $_SESSION["operand1"]  ?? 0;
    $op2       = $_SESSION["operand2"]  ?? 0;
    $input     = "";
    $message   = "???";

    if ($_POST["max_value"]) {
        $_SESSION["max_value"] = $_POST["max_value"];
    }

    if ($_POST["sign"]) {
        $_SESSION["sign"] = $_POST["sign"];
    }

    if ($_POST["generate"]) {
        if ($sign === '*') {
            $op1 = rand(1, floor(sqrt($max_value)));
            $op2 = rand(0, floor($max_value / $op1));
        } else {
            $op1 = rand(0, $max_value);
            $op2 = match ($sign) {
                '-' => rand(0, $op1),
                '+' => rand(0, $max_value - $op1),
                default => 0
            };
        }
        $_SESSION["operand1"] = $op1;
        $_SESSION["operand2"] = $op2;
    }

    if ($_POST["check"]) {
        $correctRes = match ($sign) {
            '+' => $op1 + $op2,
            '-' => $op1 - $op2,
            '*' => $op1 * $op2,
        };
        $input = $_POST["input"];
        $message = ($input == $correctRes)? 'Правильно!' : 'Спробуй ще!';
    }
?>
<h1 class="center"> Математичний тест </h1>
<hr/>
<form name="test" method="POST">
    <table class="center">
        <tr>
            <td><button id='d1' name="max_value" value="10">0–10</td>
            <td><button id='d2' name="max_value" value="20">0–20</td>
            <td><button id='d3' name="max_value" value="41">0–41</td>
            <td><button id='d4' name="max_value" value="100">0–100</td>
            <td><button id="plus"     name="sign" value="+">+</td>
            <td><button id="minus"    name="sign" value="-">-</td>
            <td><button id="asterisk" name="sign" value="*">×</td>
        </tr>
    </table>
    <hr>
    <!--90-->
    <table class="center">
        <TR>
            <td><input id="op1" size="3" maxlength="3" value="<?php echo $op1; ?>" /></td>
            <td><input id="s_sign" size="1" maxlength="1" value="<?php echo $sign; ?>" /></td>
            <td><input id="op2" size="3" maxlength="3" value="<?php echo $op2; ?>" /></td>
            <td>=</td>
            <td><input id="result" size="4" maxlength="4" name="input" value="<?php echo $input; ?>" /></td>
            <td><input id="award" type="submit" name="generate" value="?" />
            <td><input id="r0" value="<?php echo $message; ?>" />
        </TR>
    </table>
    <hr/>
    <!--100-->
    <table id="keyboard">
        <tr>
            <td><input id="b1" type=button value="1" onClick='input_sign("1")'></td>
            <td><input id="b2" type=button value="2" onClick='input_sign("2")'></td>
            <td><input id="b3" type=button value="3" onClick='input_sign("3")'></td>
        </tr>
        <tr>
            <td><input id="b4" type=button value="4" onClick='input_sign("4")'></td>
            <td><input id="b5" type=button value="5" onClick='input_sign("5")'></td>
            <td><input id="b6" type=button value="6" onClick='input_sign("6")'></td>
        </tr>
        <tr>
            <td><input id="b7" type=button value="7" onClick='input_sign("7")'></td>
            <td><input id="b8" type=button value="8" onClick='input_sign("8")'></td>
            <td><input id="b9" type=button value="9" onClick='input_sign("9")'></td>
        </tr>
        <tr>
            <td><input id="b0" type=button value="0" onClick='input_sign("0")'></td>
            <td colspan="2"><input id="bs" type="submit" name="check" value="OK" /></td>
        </tr>
    </table>
</form>
<hr/>
<script>
    const dark_colors = ["green", "black", "blue", "gray", "saddlebrown", "purple"];
    const colors = [...dark_colors, "yellow", "red", "cyan", "orange", "white"];
    for (const c of "0123456789s".split('')) {
        let css = document.getElementById("b" + c).style;
        css.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
        css.color = dark_colors.includes(css.backgroundColor)? "white" : "black";
    }
</script>
</body>
</html>
