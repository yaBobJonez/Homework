using Microsoft.VisualBasic;

namespace OOPLR8
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void OnQuit(object sender, EventArgs e)
        {
            Close();
        }

        private void OnCountKK(object sender, EventArgs e)
        {
            string s = Interaction.InputBox("������ ��� �����:", "��������");
            int c = 0; bool isSecondK = false;
            foreach (char ch in s)
            {
                if (ch == '�' && isSecondK) { ++c; isSecondK = false; }
                else if (ch == '�') isSecondK = true;
            }
            MessageBox.Show($"�����: {s}\nʳ������ \"��\": {c}", "���������", MessageBoxButtons.OK);
        }

        private void OnRmCommasEtReplace3wPlus(object sender, EventArgs e)
        {
            string orig = Interaction.InputBox("������ ��� �����:", "��������");
            string res = "";
            foreach (char ch in orig)
                res += (ch == ',')? "" : (ch == '3')? "+" : ch;
            MessageBox.Show($"���������� �����: {orig}\n����� �����: {res}", "���������", MessageBoxButtons.OK);
        }
    }
}