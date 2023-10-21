using Microsoft.VisualBasic;

namespace OOPLR2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            new PeriodicalPublication().Info();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int inIssue, inPeriod, inPages, inWPP; string inTitle, inAuthor;
            while (!int.TryParse(Interaction.InputBox("������ ������", "��������"), out inIssue)) ;
            while (!int.TryParse(Interaction.InputBox("������ ����������� (� ����)", "��������"), out inPeriod)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� �������", "��������"), out inPages)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ��� �� �������", "��������"), out inWPP)) ;
            inTitle = Interaction.InputBox("������ �����", "��������");
            inAuthor = Interaction.InputBox("������ ������", "��������");
            new PeriodicalPublication(inIssue, inPeriod, inPages, inWPP, inTitle, inAuthor).Info();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Close();
        }
    }

    public class PeriodicalPublication
    {
        int Issue, PeriodInDays = 7, NumberOfPages, AvgWordsPerPage;
        string Title, Author = "��� ������";

        public PeriodicalPublication(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, string title, string author)
        {
            Issue = Math.Abs(issue);
            PeriodInDays = Math.Abs(periodInDays);
            AvgWordsPerPage = Math.Abs(avgWordsPerPage);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
            Author = author;
        }
        public PeriodicalPublication(int issue, int numberOfPages, string title)
        {
            Issue = Math.Abs(issue);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
        }
        public PeriodicalPublication()
        {
            Issue = 1;
            NumberOfPages = 25;
            Title = "�������� �������";
        }

        public int RelativeTimeTillIssueNumber(int nextIssue)
        {
            return Math.Abs((nextIssue - Issue) * PeriodInDays);
        }
        public int WordCount()
        {
            return NumberOfPages * AvgWordsPerPage;
        }

        public void Info()
        {
            string info = $"{Author} {Title}. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodInDays switch
            {
                1 => "�������",
                2 => "����������",
                7 => "���������",
                14 => "������������",
                >= 28 and < 32 => "��������",
                365 | 366 => "������",
                _ => (
                    PeriodInDays % 10 == 1
                        ? $"����� {PeriodInDays} ����"
                        : PeriodInDays % 10 >= 2 && PeriodInDays % 10 < 5
                            ? $"���� {PeriodInDays} ��"
                            : $"���� {PeriodInDays} ���"
                )
            };
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            MessageBox.Show(info, Title);
        }
    }
}