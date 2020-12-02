const fs = require("fs");
// b inc 5 if a > 1
// a inc 1 if b < 5
// c dec -10 if a >= 1
// c inc -20 if c == 10
const OPERATORS  = ["==", "!=", ">=", "<=", ">", "<"];

let registerToValueMap = new Map();

function findOperator(condition) {
  return OPERATORS.find(item => condition.includes(item));
}


function ifConditionHelper(operator, value, register) {
  switch(operator) {
    // "==", ">=", "<=", ">", "<", "!="
    case '==':
      return (register == value);
    case '>=':
      return (register >= value);
    case '<=':
      return (register <= value);
    case '>':
      return (register > value);
    case '<':
      return (register < value);
    case '!=':
    return (register != value);
    default:
      console.log("INVALID operator", operator);
      return false;
  }
}


function fetchRegisterMap(register) {
  console.log("INPUT", register, register.length);
  const inputRegister = register.trim();
  if (registerToValueMap.has(inputRegister)) {
    return registerToValueMap.get(inputRegister);
  }
  registerToValueMap.set(inputRegister, 0);
  return registerToValueMap.get(inputRegister);
}

// c == 10
function parseCondition(condition) {
  const operator = findOperator(condition);
  const[registerName, value] = condition.split(operator);
  let registerValue = fetchRegisterMap(registerName);
  console.log("IF CONDITION HELPER", "registerName", registerName, registerValue,  operator, value);
  return ifConditionHelper(operator, parseInt(value), parseInt(registerValue));
}


function isIncreament(statement) {
  return statement.includes("inc");
}

let MAX_VALUE_STORED_INSIDE_REGISTER = Number.MIN_VALUE;

function decreased(operand, value) {
  const inputOperand = operand.trim();
  const operandValue = fetchRegisterMap(inputOperand);

  console.log("DECREASED BY ", parseInt(operandValue), "-", parseInt(value) )
  const tempResult = parseInt(operandValue) - parseInt(value);
  console.log("RESULT AFTER INSTRUCTION IS PROCESSED", inputOperand, tempResult);
  registerToValueMap.set(inputOperand, tempResult);
  MAX_VALUE_STORED_INSIDE_REGISTER = Math.max(MAX_VALUE_STORED_INSIDE_REGISTER, tempResult);
  return;
}

function increased(operand, value) {
  const inputOperand = operand.trim();
  const operandValue = fetchRegisterMap(inputOperand);

  console.log("DECREASED BY ", parseInt(operandValue), "+", parseInt(value) )
  const tempResult = parseInt(operandValue) + parseInt(value);
  console.log("RESULT AFTER INSTRUCTION IS PROCESSED", inputOperand, tempResult);
  registerToValueMap.set(inputOperand, tempResult);
  MAX_VALUE_STORED_INSIDE_REGISTER = Math.max(MAX_VALUE_STORED_INSIDE_REGISTER, tempResult);
  return;
}
// c inc -20
function executeStatement(statement) {
  if (isIncreament(statement)) {
    const [register, value] = statement.split("inc");
    increased(register, value);
    return;
  }

  const [register, value] = statement.split("dec");
  return decreased(register, value);
  return;
}

function largestRegisterValue(instruction) {
  const [execution, condition] =  instruction.split("if");
  if (parseCondition(condition.trim())) {
    executeStatement(execution.trim());
  }
}

(function() {
  const input = fs.readFileSync('input.txt', 'utf8')
  const instructions = input.split("\n").map(item => item.trim());
  instructions.forEach(instruction => {
    console.log("INSTRUCTION", instruction);
    largestRegisterValue(instruction);
    console.log("_____________________________");
  });


  let max = Number.MIN_VALUE;
  console.log("RESULT");
  for (let [key, value] of registerToValueMap.entries()) {
    console.log(key, value);
    max = Math.max(max, value);
  }

  console.log("MAXIMUM", max);
  console.log("MAX_VALUE_STORED_INSIDE_REGISTER", MAX_VALUE_STORED_INSIDE_REGISTER);
})();