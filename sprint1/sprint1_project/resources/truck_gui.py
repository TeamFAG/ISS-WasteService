import tkinter as tk
from truck import *


selected_material = "GLASS"
connect(8050)

def update_material(mat) :
    global selected_material
    selected_material = mat

def handleSend() :
    material = selected_material
    quantity = entry.get()
    req = "msg(storeRequest, request, python, wasteservice, storeRequest("+selected_material+", "+quantity+"), 1)"
    update_text(req)
    reply = request(req)
    update_text(reply)


def update_text(new_text) :
    text.configure(state='normal')
    text.insert("1.0", new_text)
    text.configure(state='disabled')


window = tk.Tk()

greeting = tk.Label(text="Smart Device for Wasteservice")
greeting.pack()

variable = tk.StringVar(window)
variable.set("GLASS") # default value

choiceBox = tk.OptionMenu(window, variable, "GLASS", "PLASTIC", command=update_material)
choiceBox.pack()

entry = tk.Entry(width=40)
entry.pack()

text = tk.Text(window, state='disabled', width=40, height=10)
text.pack()

button = tk.Button(
    text="Send Request",
    width=10,
    height=2,
    bg="blue",
    fg="yellow",
    command=handleSend,
)
button.pack()

tk.mainloop()
