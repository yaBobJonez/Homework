import random as rng
import pytest
from pathlib import Path
from playwright.sync_api import Page, expect

@pytest.fixture(scope="function", autouse=True)
def setup(page: Page):
    page.goto(Path('./webapp/index.html').resolve().as_uri())
    yield

@pytest.mark.parametrize("sign", ('+', '-', '*'))
def test_signs(page: Page, sign: str) -> None:
    page.get_by_role("button", name="0-10", exact=True).click()
    page.get_by_role("button", name=sign, exact=True).click()
    page.get_by_role("button", name="?").click()
    expect(page.locator("#s_sign")).to_have_value(sign)

@pytest.mark.parametrize("max_value", (10, 20, 41, 100))
def test_ranges(page: Page, max_value: int) -> None:
    page.get_by_role("button", name="+").click()
    page.get_by_role("button", name=f"0-{max_value}", exact=True).click()
    for _ in range(10):
        page.get_by_role("button", name="?").click()
        assert 0 <= int(page.locator("#op1").input_value()) < max_value
        assert 0 <= int(page.locator("#op2").input_value()) < max_value

def test_keypad(page: Page) -> None:
    page.get_by_role("button", name="0-100", exact=True).click()
    page.get_by_role("button", name="+").click()
    for _ in range(5):
        digits = [str(rng.randint(0, 9)) for _ in range(4)]
        for d in digits:
            page.get_by_role("button", name=d, exact=True).click()
        page.get_by_role("button", name="OK").click()
        expect(page.locator("#result")).to_have_value("".join(digits))

def test_correctness_by_typing(page: Page) -> None:
    page.get_by_role("button", name="0-100", exact=True).click()
    page.get_by_role("button", name="+").click()
    for _ in range(10):
        page.get_by_role("button", name="?").click()
        op1 = int(page.locator("#op1").input_value())
        op2 = int(page.locator("#op2").input_value())
        correct = op1 + op2
        if bool(rng.getrandbits(1)):
            page.locator("#result").clear()
            page.locator("#result").type(str(correct))
            page.get_by_role("button", name="OK").click()
            expect(page.locator("#r0")).to_have_value("Правильно!")
        else:
            page.locator("#result").clear()
            page.locator("#result").type(str(correct // 2 + rng.randint(0, 50)))
            page.get_by_role("button", name="OK").click()
            expect(page.locator("#r0")).to_have_value("Спробуй ще!")

def test_correctness_using_keypad(page: Page) -> None:
    page.get_by_role("button", name="0-100", exact=True).click()
    page.get_by_role("button", name="+").click()
    for _ in range(10):
        page.get_by_role("button", name="?").click()
        op1 = int(page.locator("#op1").input_value())
        op2 = int(page.locator("#op2").input_value())
        correct = op1 + op2
        if bool(rng.getrandbits(1)):
            for d in str(correct):
                page.get_by_role("button", name=d, exact=True).click()
            page.get_by_role("button", name="OK").click()
            expect(page.locator("#r0")).to_have_value("Правильно!")
        else:
            for d in str(correct // 2 + rng.randint(0, 50)):
                page.get_by_role("button", name=d, exact=True).click()
            page.get_by_role("button", name="OK").click()
            expect(page.locator("#r0")).to_have_value("Спробуй ще!")
